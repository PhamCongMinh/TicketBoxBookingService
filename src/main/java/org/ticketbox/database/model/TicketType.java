package org.ticketbox.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_types")
@Builder
public class TicketType extends BaseModel {
    private long eventId;
    private String typeName;
    private long price;
    private long totalQuantity;
    private long availableQuantity;
}
