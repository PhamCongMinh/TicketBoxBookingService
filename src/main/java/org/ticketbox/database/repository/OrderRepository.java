package org.ticketbox.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticketbox.database.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
