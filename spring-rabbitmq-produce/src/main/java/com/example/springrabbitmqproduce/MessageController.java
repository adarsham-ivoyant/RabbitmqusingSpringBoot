package com.example.springrabbitmqproduce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class MessageController {

    @Autowired
    public RabbitTemplate template;

    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomMessage message) throws JsonProcessingException {
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(message);
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTINGKEY,data);
        return "Message published";
    }
}
