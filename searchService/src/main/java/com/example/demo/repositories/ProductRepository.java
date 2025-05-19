package com.example.SearchService.repositories;


import com.example.SearchService.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ProductRepository {

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    public void save(Product product) {
        int attempts = 0;
        while (attempts < 5) {
            try {
                redisTemplate.opsForValue().set("product:" + product.getId(), product);
                return;
            } catch (Exception e) {
                attempts++;
                System.err.println("❌ Redis unavailable (attempt " + attempts + "), retrying in 2s...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }
        throw new RuntimeException("❌ Failed to connect to Redis after retries");
    }


    public void delete(Long productId) {
        redisTemplate.delete("product:" + productId);
    }

    public Product findById(Long productId) {
        return redisTemplate.opsForValue().get("product:" + productId);
    }

    public List<Product> findAll() {
        Set<String> keys = redisTemplate.keys("product:*");
        List<Product> products = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                Product product = redisTemplate.opsForValue().get(key);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    // get all products
    public List<Product> getAllProducts() {
        return findAll();
    }

    // add product
    public Product addProduct(Product product) {
        save(product);
        return product;
    }
}

