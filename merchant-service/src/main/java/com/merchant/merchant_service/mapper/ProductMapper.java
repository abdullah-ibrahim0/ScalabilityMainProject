package com.merchant.merchant_service.mapper;

import com.merchant.merchant_service.dto.ProductCreateDTO;
import com.merchant.merchant_service.dto.ProductDTO;
import com.merchant.merchant_service.entity.Merchant;
import com.merchant.merchant_service.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductCreateDTO dto, Merchant merchant) {
        Product product = new Product();
        product.setMerchant(merchant);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());
        product.setDiscount(dto.getDiscount());
        return product;
    }

    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setMerchantId(product.getMerchant().getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        dto.setDiscount(product.getDiscount());
        return dto;
    }
}
