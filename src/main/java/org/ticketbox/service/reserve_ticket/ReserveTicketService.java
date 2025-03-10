package org.ticketbox.service.reserve_ticket;

import org.springframework.stereotype.Service;
import org.ticketbox.dto.order.CreateOrderDto;
import org.ticketbox.dto.order.CreateOrderItemDto;
import org.ticketbox.service.redis.RedisService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReserveTicketService {
    private final RedisService redisService;
    private final long RESERVE_TICKET_EXPIRATION = 600;
    private final String reserveTicketLuaScript;

    public ReserveTicketService(RedisService redisService) throws IOException {
        this.redisService = redisService;
        this.reserveTicketLuaScript = loadReserveTicketLuaScript("lua/reserve_ticket.lua");
    }

    private String loadReserveTicketLuaScript(String path) throws IOException {
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

    public void releaseTicket() {

    }
}
