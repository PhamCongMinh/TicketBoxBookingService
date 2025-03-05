package org.ticketbox.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticketbox.database.model.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
}
