package com.example.UserService.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

//    public static final String MERCHANT_QUEUE = "merchant.created.queue";
    public static final String EXCHANGE = "user.exchange";
    public static final String ROUTING_KEY = "merchant.created";

    // Topic Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    // Durable Queue
//    @Bean
//    public Queue merchantQueue() {
//        return new Queue(MERCHANT_QUEUE, true);
//    }

    // Binding between queue and exchange using routing key
//    @Bean
//    public Binding merchantBinding() {
//        return BindingBuilder.bind(merchantQueue()).to(exchange()).with(ROUTING_KEY);
//    }

    // JSON Converter
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate with JSON Converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}

