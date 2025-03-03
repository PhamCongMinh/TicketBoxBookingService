package org.ticketbox.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticketbox.database.model.Venue;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    Optional<Venue> getVenueById(long id);
}
