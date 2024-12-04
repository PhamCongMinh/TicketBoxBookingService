package org.ticketbox.controller.test_kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ticketbox.service.kafka.KafkaProducerService;
import org.ticketbox.shared.type.TicketTypeSummary;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String topic) {
        TicketTypeSummary ticketTypeSummary =  TicketTypeSummary.builder().ticketTypeId(1).eventId(1).amount(2).build();
        kafkaProducerService.sendMessage(topic, ticketTypeSummary);
        return "Message sent to topic: " + topic;
    }
}
