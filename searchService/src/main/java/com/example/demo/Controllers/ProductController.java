package com.example.SearchService.controllers;


import com.example.SearchService.models.Product;
import com.example.SearchService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


     @Autowired
     private ProductService productService;

     @GetMapping("/all")
     public List<Product> getAllProducts() {
         return productService.getAllProducts();
     }

     @PostMapping("/create")
     public Product createProduct(@RequestBody Product product) {
         return productService.createProduct(product);
     }
}
