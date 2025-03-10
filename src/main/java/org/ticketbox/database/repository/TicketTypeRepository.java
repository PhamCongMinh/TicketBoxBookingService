package org.ticketbox.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticketbox.database.model.TicketType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
    Optional<List<TicketType>> findByEventId(long eventId);

    Optional<List<TicketType>> findAllByIdIn(List<Long> ids);
}
