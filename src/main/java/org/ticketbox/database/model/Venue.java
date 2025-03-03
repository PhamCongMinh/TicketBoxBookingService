package org.ticketbox.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venues")
@Builder
public class Venue extends BaseModel {
    private String name;
    private String address;
    private String city;
    private Long capacity;
    private String seatMapUrl;

    @OneToOne(mappedBy = "venue")
    private Event event;
}
