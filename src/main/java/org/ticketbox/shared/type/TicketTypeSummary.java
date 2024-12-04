package org.ticketbox.shared.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeSummary {
    private long ticketTypeId;
    private long eventId;
    private Integer amount;
}
