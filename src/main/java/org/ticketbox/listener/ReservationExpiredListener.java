package org.ticketbox.listener;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.ticketbox.service.reserve_ticket.ReserveTicketService;

@AllArgsConstructor
public class ReservationExpiredListener implements MessageListener {
    private final ReserveTicketService reserveTicketService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        System.out.println("Reservation expired for key: " + expiredKey);

        if (expiredKey.startsWith("reserve_ticket:")) {
            String[] parts = expiredKey.split(":");
            long orderId = Long.parseLong(parts[1]);
            reserveTicketService.releaseTicket(orderId);
        } else {
            System.out.println("Unknown key expired: " + expiredKey);
        }
    }
}
