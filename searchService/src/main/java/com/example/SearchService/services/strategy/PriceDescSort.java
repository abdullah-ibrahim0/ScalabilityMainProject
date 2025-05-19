package com.example.SearchService.services.strategy;


import com.example.SearchService.models.Product;

import java.util.Comparator;
import java.util.List;

public class PriceDescSort implements SortStrategy {
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .toList();
    }
}
