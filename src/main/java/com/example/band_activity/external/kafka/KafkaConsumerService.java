package com.example.band_activity.external.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "activity-event-topic", groupId = "activity-consumer-group")
    public void consume(String message) throws IOException {
        System.out.printf("Consumed Message : %s%n", message);
    }
}
