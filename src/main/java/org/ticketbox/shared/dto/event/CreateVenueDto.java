package org.ticketbox.shared.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVenueDto {
    private String name;
    private String address;
    private String city;
    private Long capacity;
    private String seatMapUrl;
}
