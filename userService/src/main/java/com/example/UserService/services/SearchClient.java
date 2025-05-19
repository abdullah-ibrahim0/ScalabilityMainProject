package com.example.UserService.services;

import com.example.UserService.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SearchClient {

    private final RestTemplate restTemplate;


    @Value("${services.search-service.url}")
    private String searchServiceUrl;


    public SearchClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> search(String name, String category, String sort) {
        String url = searchServiceUrl + "/search?";

        if (name != null) url += "name=" + name + "&";
        if (category != null) url += "category=" + category + "&";
        if (sort != null) url += "sort=" + sort;

        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
        return List.of(response.getBody());
    }
}
