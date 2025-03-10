package org.ticketbox.dto.ticket_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeDto {
    private long eventId;
    private TicketTypeDto[] ticketTypes;
}

