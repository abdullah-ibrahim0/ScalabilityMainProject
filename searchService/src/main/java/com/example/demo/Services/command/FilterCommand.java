package com.example.SearchService.services.command;


import com.example.SearchService.models.Product;

import java.util.List;

public interface FilterCommand {
    List<Product> apply(List<Product> products);
}
