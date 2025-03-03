package org.ticketbox.shared.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditEventDto extends CreateEventDto {
    private EditVenueDto venue;
}
