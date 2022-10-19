package com.example.rabbitmq.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "${message.queue}")
    private void receiveMessage(String message) {
        System.out.println(String.format("Received message: %s", message));
    }
}
