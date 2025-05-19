package com.example.SearchService.services;


import com.example.SearchService.models.Product;
import com.example.SearchService.repositories.ProductRepository;
import com.example.SearchService.services.command.CategoryFilter;
import com.example.SearchService.services.command.FilterCommand;
import com.example.SearchService.services.command.NameFilter;
import com.example.SearchService.services.strategy.SortStrategy;
import com.example.SearchService.services.strategy.SortStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> search(String name, String category, String sort) {
        List<Product> allProducts = productRepository.findAll();

        // Apply filter commands
        List<FilterCommand> filters = List.of(
                new NameFilter(name),
                new CategoryFilter(category)
        );

        List<Product> filtered = allProducts;
        for (FilterCommand f : filters) {
            filtered = f.apply(filtered);
        }


        // Apply sort strategy
        SortStrategy sorter = SortStrategyFactory.get(sort);
        return sorter.sort(filtered);
    }
}

