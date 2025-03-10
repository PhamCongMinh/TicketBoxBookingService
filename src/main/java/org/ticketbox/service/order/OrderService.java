package org.ticketbox.service.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.ticketbox.database.model.Order;
import org.ticketbox.database.model.OrderItem;
import org.ticketbox.database.model.TicketType;
import org.ticketbox.database.repository.OrderItemRepository;
import org.ticketbox.database.repository.OrderRepository;
import org.ticketbox.database.repository.TicketRepository;
import org.ticketbox.database.repository.TicketTypeRepository;
import org.ticketbox.dto.order.CreateOrderDto;
import org.ticketbox.dto.order.CreateOrderItemDto;
import org.ticketbox.service.reserve_ticket.ReserveTicketService;
import org.ticketbox.shared.constant.ErrorCodeConstant;
import org.ticketbox.shared.exception.custom.BadRequestException;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ReserveTicketService reserveTicketService;

    public Order createOrder(CreateOrderDto order) {
        // Check if order is valid
        List<TicketType> ticketTypes = checkTicketTypesBeforeCreateNewOrder(order);

        // Check available ticket amount is enough
        // If enough, reserve ticket amount for user in 10 minutes
        boolean isEnoughTicket = reserveTicketService.reserveTicket(order);
        if (!isEnoughTicket)
            throw new BadRequestException(ErrorCodeConstant.NOT_ENOUGH_TICKETS);

        // Todo: return pending order to user start payment
        return saveOrder(order, ticketTypes);
    }

    public List<TicketType> checkTicketTypesBeforeCreateNewOrder(CreateOrderDto order) {
        // Check max quantity of all ticket types is not greater than 8
        if (Arrays.stream(order.getOrderItems()).mapToLong(CreateOrderItemDto::getQuantity).sum() > 8)
            throw new BadRequestException(ErrorCodeConstant.MAX_TICKET_QUANTITY_EXCEEDED);

        List<Long> ticketTypeIds = Arrays.stream(order.getOrderItems()).map(CreateOrderItemDto::getTicketTypeId).toList();
        List<TicketType> ticketTypes = ticketTypeRepository.findAllByIdIn(ticketTypeIds)
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.TICKET_TYPE_NOT_EXIST));

        // Check if all ticket types are valid
        long validTicketTypesNumber = ticketTypes.stream().filter((ticketType) -> ticketType.getEventId() == order.getEventId()).count();
        if (validTicketTypesNumber != Arrays.stream(order.getOrderItems()).count())
            throw new BadRequestException(ErrorCodeConstant.LIST_TICKET_TYPE_NOT_VALID);

        return ticketTypes;
    }

    Order saveOrder(CreateOrderDto orderData, List<TicketType> ticketTypes) {
        Order newOrder = new Order();
        newOrder.setEventId(orderData.getEventId());
        newOrder.setUserId(orderData.getUserId());
        newOrder.setStatus("init");
        newOrder.setTotalAmount(calculateOrderTotalAmount(orderData, ticketTypes));
        newOrder.setOrderItems(Arrays.stream(orderData.getOrderItems()).map((orderItem) -> {
            OrderItem orderItemEntity = new OrderItem();
            orderItemEntity.setInventoryMethod("QUANTITY");
            orderItemEntity.setQuantity(orderItem.getQuantity());
            orderItemEntity.setTicketType(ticketTypes.stream().filter((item)-> item.getId() == orderItem.getTicketTypeId()).findFirst().get());
            return orderItemEntity;
        }).toList());
        return orderRepository.save(newOrder);
    }

    long calculateOrderTotalAmount(CreateOrderDto order, List<TicketType> ticketTypes) {
        return Arrays.stream(order.getOrderItems()).mapToLong((ticketType) -> {
            TicketType matchTicketType = ticketTypes.stream().filter((item) -> item.getId() == ticketType.getTicketTypeId()).findFirst().get();
            return matchTicketType.getPrice() * ticketType.getQuantity();
        }).sum();
    }

    public Order getOrder(Order order) {
        return null;
    }

    public Order updateOrder(Order order) {
        return null;
    }

    public void payOrder(Order order) {
    }

    public void cancelOrder(Order order) {
    }
}
