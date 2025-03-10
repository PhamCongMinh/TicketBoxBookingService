package org.ticketbox.service.ticket_type;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.ticketbox.database.model.Ticket;
import org.ticketbox.database.model.TicketType;
import org.ticketbox.database.repository.TicketTypeRepository;
import org.ticketbox.dto.ticket_type.CreateTicketTypeDto;
import org.ticketbox.service.redis.RedisService;
import org.ticketbox.shared.constant.ErrorCodeConstant;
import org.ticketbox.shared.exception.custom.BadRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;
    private final RedisService redisService;

    public List<TicketType> createTicketType(CreateTicketTypeDto dto) {
        List<TicketType> newTicketTypes = saveTicketType(dto);
        saveAvailableTicketTypesToRedis(newTicketTypes);
        return newTicketTypes;
    }

    List<TicketType> saveTicketType(CreateTicketTypeDto dto) {
        List<TicketType> ticketTypes = Arrays.stream(dto.getTicketTypes()).map((ticketTypeDto) -> {
            TicketType ticketType = new TicketType();
            ticketType.setName(ticketTypeDto.getName());
            ticketType.setPrice(ticketTypeDto.getPrice());
            ticketType.setTotalQuantity(ticketTypeDto.getTotalQuantity());
            ticketType.setAvailableQuantity(ticketTypeDto.getTotalQuantity());
            ticketType.setEventId(dto.getEventId());
            return ticketType;
        }).collect(Collectors.toList());
        return ticketTypeRepository.saveAll(ticketTypes);
    }

    void saveAvailableTicketTypesToRedis(List<TicketType> ticketTypes) {
        ticketTypes.forEach(ticketType -> {
            String key = "ticket:" + ticketType.getId() + ":available";
            // Set available quantity to Redis with expiration time
            // Expiration time is event end time
            redisService.setValue(key, ticketType.getAvailableQuantity());
        });
    }

    public List<TicketType> getTicketTypesOfEvent(long eventId) {
        return ticketTypeRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException(ErrorCodeConstant.TICKET_TYPE_NOT_EXIST));
    }
}
