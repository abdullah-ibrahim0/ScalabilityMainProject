package com.example.SearchService.services;

import com.example.SearchService.models.Product;
import com.example.SearchService.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    // implement for me get all, add

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        // Logic to retrieve all products from the database
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        // Logic to save the product to the database
        productRepository.save(product);
        return product;
    }
}
