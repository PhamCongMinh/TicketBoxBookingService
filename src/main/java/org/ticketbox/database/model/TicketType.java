package org.ticketbox.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_types")
@Builder
public class TicketType extends BaseModel {
    private long eventId;
    private String name;
    private long price;
    private long totalQuantity;
    private long availableQuantity;

    @OneToMany(mappedBy = "ticketType")
    private List<OrderItem> orderItems;
}
