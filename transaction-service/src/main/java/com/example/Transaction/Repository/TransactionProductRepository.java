package com.example.Transaction.Repository;

import com.example.Transaction.Model.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionProductRepository extends JpaRepository<TransactionProduct, Long> {
    List<TransactionProduct> findByTransactionId(Long transactionId);
}