package com.merchant.merchant_service.publisher;

import com.merchant.merchant_service.config.RabbitMQConfig;
import com.merchant.merchant_service.dto.ProductDTO;
import com.merchant.merchant_service.entity.Product;
import com.merchant.merchant_service.mapper.ProductMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ProductEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Product product, String action) {
        ProductDTO dto = ProductMapper.toDTO(product);
        System.out.println(dto.getMerchantId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRODUCT_EXCHANGE,
                "product." + action,
                dto
        );

        System.out.printf("📤 Published RabbitMQ event: product.%s → ID=%d%n", action, product.getId());
    }
}
