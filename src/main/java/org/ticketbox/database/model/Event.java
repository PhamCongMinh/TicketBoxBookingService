package org.ticketbox.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
@Builder
public class Event extends BaseModel {
    private String name;
    private String backgroundImageUrl;
    private Date startTime;
    private Date endTime;
    private String description;
    private String status;

    @ManyToOne()
    @JoinColumn(name = "organizer_id")
    @JsonIgnoreProperties("events")
    private Organizer organizer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venue_id", referencedColumnName = "id")
    @JsonBackReference
    private Venue venue;
}
