package org.ticketbox.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticketbox.database.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
