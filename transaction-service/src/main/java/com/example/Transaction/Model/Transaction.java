package com.example.Transaction.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "transactions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long  userId;

    @Column
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;


    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "transaction",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    @JsonManagedReference("txn-items")
    private List<TransactionProduct> items = new ArrayList<>();
}
