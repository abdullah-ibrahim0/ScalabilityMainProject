package com.merchant.merchant_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemsDTO {

    private Long id;
    private Long orderId;
    private Long productId;
    private Long merchantId;
    private Integer quantity;
    private LocalDateTime createdAt;

    public OrderItemsDTO() {}

    public OrderItemsDTO(Long id, Long orderId, Long productId, Long merchantId,
                         Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.merchantId = merchantId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public Integer getQuantity() {
        return quantity;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
