package org.ticketbox.controller.booking;

import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.Order;
import org.ticketbox.database.model.TicketType;
import org.ticketbox.service.booking.BookingService;
import org.ticketbox.shared.base.BaseResponse;

@RestController()
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/ticket-types/{eventId}")
    public BaseResponse<TicketType[]> getTicketTypesOfEvent(@PathVariable long eventId) {
        return new BaseResponse<TicketType[]>(new TicketType[0]);
    }

    @PostMapping("/orders")
    public BaseResponse<Order> createOrder(Order order) {
        return new BaseResponse<Order>(bookingService.createOrder(order));
    }

    @GetMapping("/orders")
    public BaseResponse<Order> getOrder(Order order) {
        return new BaseResponse<Order>(order);
    }

    @PutMapping("/orders")
    public BaseResponse<Order> updateOrder(Order order) {
        return new BaseResponse<Order>(order);
    }

    @PostMapping("/orders/{orderId}/pay")
    public void payOrder(Order order) {
    }

    @PostMapping("/orders/cancel")
    public void cancelOrder(Order order) {
        bookingService.cancelOrder(order);
    }
}
