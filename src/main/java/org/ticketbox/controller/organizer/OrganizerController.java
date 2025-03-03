package org.ticketbox.controller.organizer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.Organizer;
import org.ticketbox.service.organizer.OrganizerService;
import org.ticketbox.shared.base.BaseResponse;

@RestController()
@RequestMapping("/organizers")
@Tag(name="Organizer", description = "Organizer API")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    @Operation(summary = "Create organizer", description = "Create organizer")
    @ApiResponse(responseCode = "200", description = "OK")
    public BaseResponse<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        return new BaseResponse<Organizer>(organizerService.createOrganizer(organizer));
    }

    @GetMapping("/{id}")
    public BaseResponse<Organizer> getOrganizerById(@PathVariable Integer id) {
        return new BaseResponse<Organizer>(organizerService.getOrganizerById(id));
    }

    @PutMapping("/{id}")
    public BaseResponse<Organizer> editOrganizer(@PathVariable Integer id, @RequestBody Organizer organizer) {
        return new BaseResponse<Organizer>(organizerService.editOrganizer(id, organizer));
    }

    @DeleteMapping("/{id}")
    public void deleteTicketTypeById(@PathVariable Integer id) {
        organizerService.deleteOrganizerById(id);
    }
}
