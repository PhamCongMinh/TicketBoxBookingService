package org.ticketbox.shared.dto.ticket_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeDto {
    private String name;

    private String price;

    private String description;

    private Integer amount;

    private Integer eventId;
}
