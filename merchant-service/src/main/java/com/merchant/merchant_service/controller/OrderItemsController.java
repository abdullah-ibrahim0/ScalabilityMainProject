package com.merchant.merchant_service.controller;

import com.merchant.merchant_service.dto.OrderItemsCreateDTO;
import com.merchant.merchant_service.dto.OrderItemsDTO;
import com.merchant.merchant_service.dto.SalesReportDTO;
import com.merchant.merchant_service.enums.ProductCategory;
import com.merchant.merchant_service.service.OrderItemsService;
import com.merchant.merchant_service.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;
    private final RequestUtils requestUtils;

    @Autowired
    public OrderItemsController(OrderItemsService orderItemsService, RequestUtils requestUtils) {
        this.orderItemsService = orderItemsService;
        this.requestUtils = requestUtils;
    }

    // ✅ Sales report with optional filters
    @GetMapping("/sales-report")
    public ResponseEntity<SalesReportDTO> getSalesReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) ProductCategory category,
            HttpServletRequest request
    ) {
        Long merchantId = requestUtils.extractMerchantId(request);
        SalesReportDTO report = orderItemsService.getSalesReport(merchantId, startDate, endDate, productId, category);
        return ResponseEntity.ok(report);
    }
}
