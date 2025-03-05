package org.ticketbox.service.booking;

import org.springframework.stereotype.Service;
import org.ticketbox.database.model.Order;
import org.ticketbox.database.repository.OrderItemRepository;
import org.ticketbox.database.repository.OrderRepository;
import org.ticketbox.database.repository.TicketRepository;

@Service
public class BookingService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final TicketRepository ticketRepository;

    public BookingService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            TicketRepository ticketRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.ticketRepository = ticketRepository;
    }

    public Order createOrder(Order order) {
        return null;
    }

    public void cancelOrder(Order order) {
    }
}
