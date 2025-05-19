package com.example.SearchService.controllers;

import com.example.SearchService.models.Product;


import com.example.SearchService.services.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ProductSearchService searchService;

    @GetMapping
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort
    ) {
        return searchService.search(name, category, sort);
    }

}
