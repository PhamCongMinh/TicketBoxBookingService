package org.ticketbox.controller.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticketbox.database.model.Event;
import org.ticketbox.service.event.EventService;
import org.ticketbox.shared.base.BaseResponse;
import org.ticketbox.shared.dto.event.CreateEventDto;
import org.ticketbox.shared.dto.event.EditEventDto;

import java.util.List;

@RestController()
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public BaseResponse<List<Event>> getAllEvent() {
        return new BaseResponse<List<Event>>(eventService.getAllEvent());
    }

    @GetMapping("/summary")
    public BaseResponse<List<Event>> getASummaryListOfEvents() {
        return new BaseResponse<List<Event>>(eventService.getAllEvent());
    }

    @GetMapping("/{id}")
    public BaseResponse<Event> getDetailEventById(@PathVariable Integer id) {
        return new BaseResponse<Event>(eventService.getDetailEventById(id));
    }

    @PostMapping
    public BaseResponse<Event> createEvent(@RequestBody CreateEventDto eventDto) {
        return new BaseResponse<Event>(eventService.createEvent(eventDto));
    }

    @PutMapping("/{id}")
    public BaseResponse<Event> editEvent(@PathVariable Integer id, @RequestBody EditEventDto eventDto) {
        return new BaseResponse<Event>(eventService.editEvent(id, eventDto));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventService.deleteEventById(id);
    }
}
