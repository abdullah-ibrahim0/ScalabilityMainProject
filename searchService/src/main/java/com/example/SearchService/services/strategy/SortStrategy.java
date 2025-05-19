package com.example.SearchService.services.strategy;


import com.example.SearchService.models.Product;

import java.util.List;

public interface SortStrategy {
    List<Product> sort(List<Product> products);
}
