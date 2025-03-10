package org.ticketbox.controller.order;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.Order;
import org.ticketbox.dto.order.CreateOrderDto;
import org.ticketbox.service.order.OrderService;
import org.ticketbox.shared.base.BaseResponse;

@RestController()
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    public BaseResponse<Order> createOrder(@RequestBody CreateOrderDto dto ) {
        return new BaseResponse<Order>(orderService.createOrder(dto));
    }

    @GetMapping("/")
    public BaseResponse<Order> getOrder(Order order) {
        return new BaseResponse<Order>(order);
    }

    @PutMapping("/")
    public BaseResponse<Order> updateOrder(Order order) {
        return new BaseResponse<Order>(order);
    }

    @PostMapping("/{orderId}/pay")
    public void payOrder(Order order) {
    }

    @PostMapping("/cancel")
    public void cancelOrder(Order order) {
        orderService.cancelOrder(order);
    }
}
