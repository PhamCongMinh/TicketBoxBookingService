package org.ticketbox.dto.ticket_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDto {
    private String name;
    private long price;
    private long totalQuantity;
}