package com.merchant.merchant_service.dto;

import com.merchant.merchant_service.enums.ProductCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductCreateDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 150)
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    private Integer stock = 0;

    @DecimalMin(value = "0.0", inclusive = true, message = "Discount cannot be less than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount cannot exceed 100")
    private Integer discount = 0;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
