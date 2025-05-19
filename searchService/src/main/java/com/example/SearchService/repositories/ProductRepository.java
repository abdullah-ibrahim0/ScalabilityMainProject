package com.example.SearchService.repositories;

import com.example.SearchService.enums.ProductCategory;
import com.example.SearchService.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // You can add custom query methods as needed, for example:
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(ProductCategory category);
}
