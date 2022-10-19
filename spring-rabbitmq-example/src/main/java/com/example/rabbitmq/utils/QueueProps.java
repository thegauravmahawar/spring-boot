package com.example.rabbitmq.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueProps {

    @Value("${message.queue}")
    private String messageQueue;

    @Value("${message.queue.exchange}")
    private String messageExchange;

    @Value("${message.queue.routing-key}")
    private String messageQueueRoutingKey;

    public String getMessageQueue() {
        return messageQueue;
    }

    public String getMessageExchange() {
        return messageExchange;
    }

    public String getMessageQueueRoutingKey() {
        return messageQueueRoutingKey;
    }
}
