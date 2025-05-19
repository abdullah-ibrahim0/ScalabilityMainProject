package com.merchant.merchant_service.mapper;

import com.merchant.merchant_service.dto.OrderItemsCreateDTO;
import com.merchant.merchant_service.dto.OrderItemsDTO;
import com.merchant.merchant_service.entity.OrderItems;
import com.merchant.merchant_service.entity.Product;

public class OrderItemsMapper {

    public static OrderItems toEntity(OrderItemsCreateDTO dto, Product product) {
        if (dto == null || product == null) return null;

        OrderItems entity = new OrderItems();
        entity.setOrderId(dto.getOrderId());
        entity.setProduct(product);
        entity.setMerchantId(dto.getMerchantId());
        entity.setQuantity(dto.getQuantity());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public static OrderItemsDTO toDTO(OrderItems entity) {
        if (entity == null) return null;

        return new OrderItemsDTO(
                entity.getId(),
                entity.getOrderId(),
                entity.getProduct().getId(),
                entity.getMerchantId(),
                entity.getQuantity(),
                entity.getCreatedAt()
        );
    }
}
