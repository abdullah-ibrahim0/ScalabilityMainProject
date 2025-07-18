package com.example.Transaction.Repository;

import com.example.Transaction.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long prodId);

}
