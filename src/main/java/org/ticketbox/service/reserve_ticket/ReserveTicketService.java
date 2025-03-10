package org.ticketbox.service.reserve_ticket;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.ticketbox.database.model.Order;
import org.ticketbox.database.repository.OrderRepository;
import org.ticketbox.dto.order.CreateOrderDto;
import org.ticketbox.service.redis.RedisService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReserveTicketService {
    private final RedisService redisService;
    private final long RESERVE_TICKET_EXPIRATION = 60;
    private final String reserveTicketLuaScript;
    private final String releaseTicketLuaScript;
    private final OrderRepository orderRepository;

    public ReserveTicketService(RedisService redisService, OrderRepository orderRepository) throws IOException {
        this.redisService = redisService;
        this.reserveTicketLuaScript = loadLuaScript("lua/reserve_ticket.lua");
        this.orderRepository = orderRepository;
        this.releaseTicketLuaScript = loadLuaScript("lua/release_ticket.lua");
    }

    private String loadLuaScript(String path) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Reserve Ticket Lua Script not found" + path);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public boolean reserveTicket(CreateOrderDto order) {
        List<String> keys = new ArrayList<>();
        List<Long> args = new ArrayList<>();
        args.add(order.getEventId());
        args.add(order.getUserId());
        args.add(RESERVE_TICKET_EXPIRATION);

        Arrays.stream(order.getOrderItems()).forEach(orderItemDto -> {
            args.add(orderItemDto.getTicketTypeId());
            args.add(orderItemDto.getQuantity());
        });
        return redisService.executeLuaScript(reserveTicketLuaScript, Boolean.class, keys, args.toArray());
    }

    public void saveExpiredTimeForReserveTicketOrder(Order order) {
        redisService.setValue("reserve_ticket:" + order.getId(), order.getId(), RESERVE_TICKET_EXPIRATION, TimeUnit.SECONDS);
    }

    @Transactional
    public void releaseTicket(long orderId) {
        // Release the reserved tickets in Redis
        System.out.println("Reserve Ticket released: " + orderId);
        // If failed, send notification email for check and save necessary information to database
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<String> keys = new ArrayList<>();
        List<Long> args = new ArrayList<>();

        order.getOrderItems().forEach(orderItem -> {
            args.add(orderItem.getTicketType().getId());
            args.add(orderItem.getQuantity());
        });

        redisService.executeLuaScript(releaseTicketLuaScript, Boolean.class, keys, args.toArray());
    }
}
