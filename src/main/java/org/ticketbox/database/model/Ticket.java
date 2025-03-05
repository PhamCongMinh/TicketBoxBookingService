package org.ticketbox.database.model;

import jakarta.persistence.CascadeType;
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
@Table(name = "tickets")
@Builder
// Using for fixed seat events
public class Ticket extends BaseModel {
    private long eventId;
    private String seatNumber;
    private long price;
    // AVAILABLE, LOCKED, SOLD, CANCELED
    private String status;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
