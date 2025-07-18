package com.merchant.merchant_service.repository;

import com.merchant.merchant_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByMerchantId(Long merchantId);

}
