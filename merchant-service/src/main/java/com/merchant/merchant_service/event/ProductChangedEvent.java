package com.merchant.merchant_service.event;

import com.merchant.merchant_service.entity.Product;
import org.springframework.context.ApplicationEvent;

public class ProductChangedEvent extends ApplicationEvent {

    private final Product product;
    private final String action; // "created", "updated", "deleted"

    public ProductChangedEvent(Object source, Product product, String action) {
        super(source);
        this.product = product;
        this.action = action;
    }

    public Product getProduct() {
        return product;
    }

    public String getAction() {
        return action;
    }
}

