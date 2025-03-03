package org.ticketbox.service.event;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.ticketbox.database.model.Event;
import org.ticketbox.database.model.Organizer;
import org.ticketbox.database.model.Venue;
import org.ticketbox.database.repository.EventRepository;
import org.ticketbox.database.repository.OrganizerRepository;
import org.ticketbox.database.repository.VenueRepository;
import org.ticketbox.shared.constant.ErrorCodeConstant;
import org.ticketbox.shared.dto.event.CreateEventDto;
import org.ticketbox.shared.dto.event.CreateVenueDto;
import org.ticketbox.shared.dto.event.EditEventDto;
import org.ticketbox.shared.exception.custom.BadRequestException;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private OrganizerRepository organizerRepository;
    private VenueRepository venueRepository;

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Transactional()
    public Event createEvent(CreateEventDto eventDto) {
        Organizer organizer = organizerRepository.getOrganizerById(eventDto.getOrganizerId())
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.ORGANIZER_NOT_EXIST));

        Venue venue = new Venue();
        this.updateVenueFromDto(venue, eventDto.getVenue());
        Venue newVenue = venueRepository.save(venue);

        Event event = new Event();
        this.updateEventFromDto(event, eventDto, newVenue, organizer);
        return eventRepository.save(event);
    }

    private void updateVenueFromDto(Venue venue, CreateVenueDto venueDto) {
        venue.setName(venueDto.getName());
        venue.setAddress(venueDto.getAddress());
        venue.setCity(venueDto.getCity());
        venue.setCapacity(venueDto.getCapacity());
        venue.setSeatMapUrl(venueDto.getSeatMapUrl());
    }

    private void updateEventFromDto(Event event, CreateEventDto eventDto, Venue newVenue, Organizer organizer) {
        event.setName(eventDto.getName());
        event.setBackgroundImageUrl(eventDto.getBackgroundImageUrl());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setVenue(newVenue);
        event.setDescription(eventDto.getDescription());
        event.setStatus(eventDto.getStatus());
        event.setOrganizer(organizer);
    }

    @Transactional()
    public Event editEvent(Integer id, EditEventDto eventDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.EVENT_NOT_EXIST));

        Organizer organizer = organizerRepository.getOrganizerById(eventDto.getOrganizerId())
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.ORGANIZER_NOT_EXIST));

        Venue venue = venueRepository.getVenueById(eventDto.getVenue().getId())
                        .orElseThrow(()-> new BadRequestException(ErrorCodeConstant.VENUE_NOT_EXIST));

        this.updateVenueFromDto(venue, eventDto.getVenue());
        Venue updatedVenue = venueRepository.save(venue);

        this.updateEventFromDto(event, eventDto, updatedVenue, organizer);

        return eventRepository.save(event);
    }

    public Event getDetailEventById(Integer id) {
        return eventRepository.getEventById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.EVENT_NOT_EXIST));
    }

    @Transactional()
    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }
}
