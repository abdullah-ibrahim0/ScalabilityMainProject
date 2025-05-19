package com.example.UserService.services;


import com.example.UserService.config.RabbitMQConfig;
import com.example.UserService.models.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import org.springframework.stereotype.Service;

@Service
public class MerchantProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMerchantCreatedEvent(User merchant) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,                // Exchange name
                RabbitMQConfig.ROUTING_KEY,             // Routing key
                merchant                                // Payload
        );
        System.out.println("✅ Sent merchant to queue: " + merchant.getName());
    }

}


