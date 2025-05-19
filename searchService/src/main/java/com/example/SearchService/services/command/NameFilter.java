package com.example.SearchService.services.command;


import com.example.SearchService.models.Product;

import java.util.List;

public class NameFilter implements FilterCommand {

    private final String keyword;

    public NameFilter(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        if (keyword == null) return products;
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}

