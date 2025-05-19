package com.merchant.merchant_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // === USER EVENT CONFIGURATION ===
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_TO_MERCHANT_QUEUE = "merchant-service.user";

    public static final String USER_DLQ = "merchant-service.user.dlq";
    public static final String USER_DLQ_EXCHANGE = "user.dlx";

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public TopicExchange userDLX() {
        return new TopicExchange(USER_DLQ_EXCHANGE);
    }

    @Bean
    public Queue userToMerchantQueue() {
        return QueueBuilder.durable(USER_TO_MERCHANT_QUEUE)
                .withArgument("x-dead-letter-exchange", USER_DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "user.failed")
                .build();
    }

    @Bean
    public Queue userDLQ() {
        return QueueBuilder.durable(USER_DLQ).build();
    }

    @Bean
    public Binding userQueueBinding() {
        return BindingBuilder
                .bind(userToMerchantQueue())
                .to(userExchange())
                .with("merchant.created");
    }

    @Bean
    public Binding userDLQBinding() {
        return BindingBuilder
                .bind(userDLQ())
                .to(userDLX())
                .with("user.failed");
    }

    // === PRODUCT EVENT CONFIGURATION ===
    public static final String PRODUCT_EXCHANGE = "product.exchange";

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    // === ORDER ITEMS EVENT CONFIGURATION ===
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_ITEMS_QUEUE = "merchant-service.order-items";

    public static final String ORDER_ITEMS_DLQ = "merchant-service.order-items.dlq";
    public static final String ORDER_ITEMS_DLQ_EXCHANGE = "order.dlx";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public TopicExchange orderItemsDLX() {
        return new TopicExchange(ORDER_ITEMS_DLQ_EXCHANGE);
    }

    @Bean
    public Queue orderItemsQueue() {
        return QueueBuilder.durable(ORDER_ITEMS_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_ITEMS_DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "order.items.failed")
                .build();
    }

    @Bean
    public Queue orderItemsDLQ() {
        return QueueBuilder.durable(ORDER_ITEMS_DLQ).build();
    }

    @Bean
    public Binding orderItemsBinding() {
        return BindingBuilder
                .bind(orderItemsQueue())
                .to(orderExchange())
                .with("order.items.created");
    }

    @Bean
    public Binding orderItemsDLQBinding() {
        return BindingBuilder
                .bind(orderItemsDLQ())
                .to(orderItemsDLX())
                .with("order.items.failed");
    }

    // === JSON Message Converter ===
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(mapper);
    }
    // === RabbitTemplate for Publishing Messages ===
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
