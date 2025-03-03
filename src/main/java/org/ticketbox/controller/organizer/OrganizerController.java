package org.ticketbox.controller.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.Organizer;
import org.ticketbox.service.organizer.OrganizerService;
import org.ticketbox.shared.base.BaseResponse;

@RestController()
@RequestMapping("/organizers")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;

    @PostMapping
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
