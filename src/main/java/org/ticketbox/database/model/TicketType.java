package org.ticketbox.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_types")
public class TicketType extends BaseModel {
    private String name;

    private String price;

    private String description;

    private Integer amount;

    @ManyToOne()
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties("ticketTypes")
    private Event event;
}
