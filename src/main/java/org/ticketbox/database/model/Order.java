package org.ticketbox.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Builder
public class Order extends BaseModel {
    private long eventId;
    private long userId;
    private long totalAmount;
    // PENDING, CONFIRMED, CANCELED, REFUNDED
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order")
    private Payment payment;
}
