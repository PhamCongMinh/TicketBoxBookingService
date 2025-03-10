package org.ticketbox.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    private long userId;
    private long eventId;
    private CreateOrderItemDto[] orderItems;
}
