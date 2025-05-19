package com.example.SearchService.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "product.exchange";

    public static final String PRODUCT_CREATE_QUEUE = "product.create.queue";
    public static final String PRODUCT_UPDATE_QUEUE = "product.update.queue";
    public static final String PRODUCT_DELETE_QUEUE = "product.delete.queue";

    public static final String ROUTING_KEY_CREATE = "product.created";
    public static final String ROUTING_KEY_UPDATE = "product.updated";
    public static final String ROUTING_KEY_DELETE = "product.deleted";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue createQueue() {
        return new Queue(PRODUCT_CREATE_QUEUE, true);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(PRODUCT_UPDATE_QUEUE, true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(PRODUCT_DELETE_QUEUE, true);
    }

    @Bean
    public Binding createBinding() {
        return BindingBuilder.bind(createQueue()).to(exchange()).with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateQueue()).to(exchange()).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding deleteBinding() {
        return BindingBuilder.bind(deleteQueue()).to(exchange()).with(ROUTING_KEY_DELETE);
    }
}
