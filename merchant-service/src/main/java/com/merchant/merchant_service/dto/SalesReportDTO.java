package com.merchant.merchant_service.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesReportDTO {

    private List<SalesReportItemDTO> items;
    private BigDecimal totalRevenue;

    public SalesReportDTO(List<SalesReportItemDTO> items, BigDecimal totalRevenue) {
        this.items = items;
        this.totalRevenue = totalRevenue;
    }

    // Getters and setters
    public List<SalesReportItemDTO> getItems() { return items; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }

    public void setItems(List<SalesReportItemDTO> items) { this.items = items; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}
