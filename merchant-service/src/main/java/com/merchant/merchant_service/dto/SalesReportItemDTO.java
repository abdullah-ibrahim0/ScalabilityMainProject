package com.merchant.merchant_service.dto;

import com.merchant.merchant_service.enums.ProductCategory;
import java.math.BigDecimal;

public class SalesReportItemDTO {

    private Long productId;
    private String productName;
    private ProductCategory category;
    private Long totalQuantitySold;
    private BigDecimal totalRevenue;

    public SalesReportItemDTO(Long productId, String productName, ProductCategory category, Long totalQuantitySold, BigDecimal totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    // Getters and setters
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public ProductCategory getCategory() { return category; }
    public Long getTotalQuantitySold() { return totalQuantitySold; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setCategory(ProductCategory category) { this.category = category; }
    public void setTotalQuantitySold(Long totalQuantitySold) { this.totalQuantitySold = totalQuantitySold; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}
