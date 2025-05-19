package com.merchant.merchant_service.repository;

import com.merchant.merchant_service.dto.SalesReportItemDTO;
import com.merchant.merchant_service.entity.OrderItems;
import com.merchant.merchant_service.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByMerchantId(Long merchantId);

}
