package com.merchant.merchant_service.service;

import com.merchant.merchant_service.dto.OrderItemsCreateDTO;
import com.merchant.merchant_service.dto.OrderItemsDTO;
import com.merchant.merchant_service.dto.SalesReportDTO;
import com.merchant.merchant_service.dto.SalesReportItemDTO;
import com.merchant.merchant_service.entity.OrderItems;
import com.merchant.merchant_service.entity.Product;
import com.merchant.merchant_service.enums.ProductCategory;
import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.exception.custom.ResourceNotFoundException;
import com.merchant.merchant_service.mapper.OrderItemsMapper;
import com.merchant.merchant_service.repository.OrderItemsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository,
                             ProductService productService) {
        this.orderItemsRepository = orderItemsRepository;
        this.productService = productService;
    }

    @Transactional
    public OrderItemsDTO createOrderItem(OrderItemsCreateDTO createDTO) {
        try {
            // Fetch product entity
            Product product = productService.getProductEntity(createDTO.getProductId());

            // Validate stock
            if (product.getStock() < createDTO.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product ID: " + product.getId());
            }

            // Subtract stock
            product.setStock(product.getStock() - createDTO.getQuantity());

            // Save product and publish update event
            productService.saveAndPublishUpdatedProduct(product);

            // Create and save order item
            OrderItems entity = OrderItemsMapper.toEntity(createDTO, product);
            OrderItems saved = orderItemsRepository.save(entity);
            return OrderItemsMapper.toDTO(saved);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Failed to create order item: " + e.getMessage());
        }
    }

    public SalesReportDTO getSalesReport(Long merchantId, LocalDateTime startDate, LocalDateTime endDate, Long productId, ProductCategory category) {
        List<OrderItems> items = orderItemsRepository.findByMerchantId(merchantId);

        List<OrderItems> filteredItems = items.stream()
                .filter(oi -> startDate == null || !oi.getCreatedAt().isBefore(startDate))
                .filter(oi -> endDate == null || !oi.getCreatedAt().isAfter(endDate))
                .filter(oi -> productId == null || oi.getProduct().getId().equals(productId))
                .filter(oi -> category == null || oi.getProduct().getCategory().equals(category))
                .toList();

        Map<Long, List<OrderItems>> groupedByProduct = filteredItems.stream()
                .collect(Collectors.groupingBy(oi -> oi.getProduct().getId()));

        List<SalesReportItemDTO> reportItems = new ArrayList<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Map.Entry<Long, List<OrderItems>> entry : groupedByProduct.entrySet()) {
            Product product = entry.getValue().get(0).getProduct();
            long totalQuantity = entry.getValue().stream().mapToLong(OrderItems::getQuantity).sum();
            BigDecimal revenue = product.getPrice().multiply(BigDecimal.valueOf(totalQuantity));

            reportItems.add(new SalesReportItemDTO(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    totalQuantity,
                    revenue
            ));

            totalRevenue = totalRevenue.add(revenue);
        }

        return new SalesReportDTO(reportItems, totalRevenue);
    }





}
