package org.ticketbox.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
@Builder
public class OrderItem extends BaseModel {
    // FIXED, QUANTITY
    private String inventoryMethod;
    private long quantity;
//    private long unitPrice;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("orderItems")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties("orderItems")
    private Ticket ticket;

    @ManyToOne()
    @JoinColumn(name = "ticket_type_id")
    @JsonIgnoreProperties("orderItems")
    private TicketType ticketType;
}
