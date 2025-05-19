package com.example.SearchService.models;

import com.example.SearchService.enums.ProductCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products") // optional
public class Product {

    @Id
    private Long id;                     // product ID (auto-incremented by merchant)

    private Long merchantId;             // reference to Merchant (no FK needed here)
    private String name;                 // required, max 150
    private String description;          // optional
    private BigDecimal price;            // decimal(10, 2)
    private Integer stock = 0;           // default 0
    private ProductCategory category;             // optional, max 50

    private Integer discount = 0; // default 0

    private BigDecimal priceAfterDiscount; // calculated field



    public BigDecimal getPriceAfterDiscount() {
        if (price == null || discount == null) return BigDecimal.ZERO;

        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal discountAmount = price.multiply(BigDecimal.valueOf(discount)).divide(hundred);
        return price.subtract(discountAmount);
    }





    // Constructor
    public Product(Long id, Long merchantId, String name, String description, BigDecimal price, Integer stock, Integer discount, ProductCategory category) {
        this.id = id;
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.discount= discount != null ? discount : 0;
        this.category = category;
    }

    // no args constructor
    public Product() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category) {
        this.category = category;
    }
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
