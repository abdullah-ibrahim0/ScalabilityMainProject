//package com.example.SearchService;
//
//
//import com.example.SearchService.enums.ProductCategory;
//import com.example.SearchService.models.Product;
//import com.example.SearchService.repositories.ProductRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Component
//public class ProductSeeder {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @PostConstruct
//    public void seed() {
//        productRepository.save(new Product(1L, 101L, "iPhone 14", "Latest iPhone model", new BigDecimal("899.99"), 20, null,ProductCategory.ELECTRONICS));
//        productRepository.save(new Product(2L, 102L, "Samsung Galaxy S22", "Android powerhouse", new BigDecimal("799.99"), 15, null, ProductCategory.ELECTRONICS));
//        productRepository.save(new Product(3L, 103L, "Apple Watch Series 8", "Stylish smartwatch", new BigDecimal("499.99"), 10,null, ProductCategory.TOYS));
//        productRepository.save(new Product(4L, 104L, "Sony WH-1000XM5", "Noise cancelling headphones", new BigDecimal("349.99"), 5, null,ProductCategory.BEAUTY));
//        productRepository.save(new Product(5L, 105L, "Dell XPS 13", "Premium ultrabook", new BigDecimal("1199.99"), 8, null, ProductCategory.HOME_APPLIANCES));
//        productRepository.save(new Product(6L, 106L, "Pixel 7 Pro", "Google's flagship phone", new BigDecimal("749.99"), 18, null, ProductCategory.SPORTS));
//
//        System.out.println("✅ Dummy products seeded to Redis.");
//    }
//}
