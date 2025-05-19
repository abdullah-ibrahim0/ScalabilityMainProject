package com.example.SearchService.services.command;


import com.example.SearchService.models.Product;

import java.util.List;

public class CategoryFilter implements FilterCommand {

    private final String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        if (category == null) return products;
        return products.stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory().toString()))
                .toList();
    }
}
