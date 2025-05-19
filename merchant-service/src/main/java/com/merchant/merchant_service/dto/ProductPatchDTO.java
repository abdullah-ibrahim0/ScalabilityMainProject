package com.merchant.merchant_service.dto;

import com.merchant.merchant_service.enums.ProductCategory;

import java.math.BigDecimal;

public class ProductPatchDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer discount;
    private ProductCategory category;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getDiscount() { return discount; }
    public void setDiscount(Integer discount) { this.discount = discount; }

    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }
}
