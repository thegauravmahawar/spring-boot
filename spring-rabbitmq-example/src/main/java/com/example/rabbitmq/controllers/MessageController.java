package com.example.rabbitmq.controllers;

import com.example.rabbitmq.utils.QueueProps;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

    private final AmqpTemplate rabbitTemplate;
    private final QueueProps queueProps;

    public MessageController(RabbitTemplate rabbitTemplate, QueueProps queueProps) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueProps = queueProps;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/send-message")
    public String sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(queueProps.getMessageExchange(), queueProps.getMessageQueueRoutingKey(), message);
        return "Message sent.";
    }
}
