package com.merchant.merchant_service.observer;

import com.merchant.merchant_service.event.ProductChangedEvent;
import com.merchant.merchant_service.entity.Product;
import com.merchant.merchant_service.publisher.ProductEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    private final ProductEventPublisher productEventPublisher;

    public ProductEventListener(ProductEventPublisher productEventPublisher) {
        this.productEventPublisher = productEventPublisher;
    }

    @EventListener
    public void handleProductChanged(ProductChangedEvent event) {
        Product product = event.getProduct();
        String action = event.getAction();

        System.out.printf("📦 Product %s: ID=%d, Name=%s%n",
                action.toUpperCase(),
                product.getId(),
                product.getName()
        );

        productEventPublisher.publish(product, action);
    }
}
