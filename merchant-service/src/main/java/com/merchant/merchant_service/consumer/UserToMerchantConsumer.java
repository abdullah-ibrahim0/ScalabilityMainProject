package com.merchant.merchant_service.consumer;

import com.merchant.merchant_service.config.RabbitMQConfig;
import com.merchant.merchant_service.dto.MerchantCreateDTO;
import com.merchant.merchant_service.service.MerchantService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserToMerchantConsumer {

    private final MerchantService merchantService;

    public UserToMerchantConsumer(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_TO_MERCHANT_QUEUE)
    public void handleUserCreated(MerchantCreateDTO dto) {
        try {
            System.out.println("📥 Received user.created event for: " + dto.getUserId());
            merchantService.registerMerchant(dto);
        } catch (Exception e) {
            System.err.println("❌ Failed to process user.created event: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
