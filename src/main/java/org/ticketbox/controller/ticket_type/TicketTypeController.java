package org.ticketbox.controller.ticket_type;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.TicketType;
import org.ticketbox.dto.ticket_type.CreateTicketTypeDto;
import org.ticketbox.service.ticket_type.TicketTypeService;
import org.ticketbox.shared.base.BaseResponse;

import java.util.List;

@RestController()
@RequestMapping("/ticket-types")
@AllArgsConstructor
@Tag(name = "Ticket Type", description = "Ticket Type API")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    // Call this API when creating an event
    // Only call from TicketBoxEventService, need mark internal API
    @PostMapping("/ticket-types")
    public BaseResponse<List<TicketType>> createTicketType(@RequestBody CreateTicketTypeDto dto) {
        return new BaseResponse<List<TicketType>>(ticketTypeService.createTicketType(dto));
    }

    @GetMapping("/{eventId}")
    public BaseResponse<List<TicketType>> getTicketTypesOfEvent(@PathVariable long eventId) {
        return new BaseResponse<List<TicketType>>(ticketTypeService.getTicketTypesOfEvent(eventId));
    }
}
