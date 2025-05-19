package com.example.SearchService.services;

import com.example.SearchService.models.Product;
import com.example.SearchService.repositories.ProductRepository;
import com.example.SearchService.services.command.CategoryFilter;
import com.example.SearchService.services.command.FilterCommand;
import com.example.SearchService.services.command.NameFilter;
import com.example.SearchService.services.strategy.SortStrategy;
import com.example.SearchService.services.strategy.SortStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Service
public class ProductSearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, List<Product>> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.merchant-service.url}")
    private String merchantServiceUrl;

    public List<Product> search(String name, String category, String sort) {
        String cacheKey = generateCacheKey(name, category, sort);
        List<Product> cachedResult = redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null) {
            System.out.println("🧠 Cache HIT for key: " + cacheKey);
            return cachedResult;
        }

        System.out.println("🔍 Cache MISS - calling merchant microservice");

        // Fetch all products from merchant microservice
        String url = merchantServiceUrl + "/products/all"; // Adjust path as needed
        List<Product> allProducts = List.of(restTemplate.getForObject(url, Product[].class));

        List<FilterCommand> filters = List.of(
                new NameFilter(name),
                new CategoryFilter(category)
        );

        for (FilterCommand f : filters) {
            allProducts = f.apply(allProducts);
        }

        SortStrategy sorter = SortStrategyFactory.get(sort);
        List<Product> result = sorter.sort(allProducts);

        redisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(5));
        return result;
    }


    private String generateCacheKey(String name, String category, String sort) {
        return "name:" + (name != null ? name.toLowerCase() : "_") +
                "-category:" + (category != null ? category.toLowerCase() : "_") +
                "-sort:" + (sort != null ? sort.toLowerCase() : "_");
    }

}
