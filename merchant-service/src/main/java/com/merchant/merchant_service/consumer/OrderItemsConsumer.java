package com.merchant.merchant_service.consumer;

import com.merchant.merchant_service.config.RabbitMQConfig;
import com.merchant.merchant_service.dto.OrderItemsCreateDTO;
import com.merchant.merchant_service.service.OrderItemsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderItemsConsumer {

    private final OrderItemsService orderItemsService;

    public OrderItemsConsumer(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_ITEMS_QUEUE)
    public void handleOrderItemsCreated(OrderItemsCreateDTO dto) {
        System.out.println("📦 Received order.items.created event for Product ID: " + dto.getProductId() +
                " | Order ID: " + dto.getOrderId());

        try {
            orderItemsService.createOrderItem(dto);
        } catch (Exception e) {
            System.err.println("❌ Failed to create order item: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
