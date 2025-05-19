package com.example.SearchService.services;

import com.example.SearchService.config.RabbitMQConfig;
import com.example.SearchService.dto.ProductDTO;
import com.example.SearchService.models.Product;
import com.example.SearchService.repositories.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductEventConsumer {

    @Autowired
    private RedisTemplate<String, List<Product>> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Product convertToProduct(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setMerchantId(dto.getMerchantId());
        product.setDiscount(dto.getDiscount());
        return product;
    }

    private boolean doesProductMatchQuery(Product product, String queryKey) {
        String lowerQuery = queryKey.toLowerCase();


        System.out.println("ALOOO89889889");
        boolean nameMatches = product.getName() != null &&
                extractValue(lowerQuery, "name:") // extract "rv" from "name:rv-category:...-sort:..."
                        .map(qName -> product.getName().toLowerCase().contains(qName))
                        .orElse(true); // If name not in query, consider it a match

        boolean categoryMatches = product.getCategory() != null &&
                lowerQuery.contains("category:" + product.getCategory().toString().toLowerCase());

        System.out.println("*(7863871283612372: " + nameMatches + " " + categoryMatches);
        return nameMatches || categoryMatches;
    }

    private Optional<String> extractValue(String query, String key) {
        int start = query.indexOf(key);
        if (start == -1) return Optional.empty();

        start += key.length();
        int end = query.indexOf("-", start);
        if (end == -1) end = query.length();

        String value = query.substring(start, end);
        return "_".equals(value) ? Optional.empty() : Optional.of(value);
    }



    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATE_QUEUE)
    public void handleProductCreate(ProductDTO dto) {
        Product product = convertToProduct(dto);
        System.out.println("📦 Received CREATE product: " + product);

        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                if (doesProductMatchQuery(product, key)) {
                    System.out.println("Adding product to cache for key: " + key);
                    List<Product> cached = redisTemplate.opsForValue().get(key);
                    if (cached == null) cached = new ArrayList<>();
                    cached.add(product);
                    redisTemplate.opsForValue().set(key, cached);
                }
            }
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_UPDATE_QUEUE)
    public void handleProductUpdate(ProductDTO dto) {
        Product product = convertToProduct(dto);
        System.out.println("✏️ Received UPDATE product: " + product);

        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                List<Product> cached = redisTemplate.opsForValue().get(key);
                if (cached == null) continue;

                boolean found = false;
                for (int i = 0; i < cached.size(); i++) {
                    if (cached.get(i).getId().equals(product.getId())) {
                        if (doesProductMatchQuery(product, key)) {
                            cached.set(i, product);
                        } else {
                            cached.remove(i);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found && doesProductMatchQuery(product, key)) {
                    cached.add(product);
                }
                redisTemplate.opsForValue().set(key, cached);
            }
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_DELETE_QUEUE)
    public void handleProductDelete(ProductDTO dto) {
        System.out.println("🗑️ Received DELETE product: " + dto);
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                List<Product> cached = redisTemplate.opsForValue().get(key);
                if (cached == null) continue;

                cached.removeIf(p -> p.getId().equals(dto.getId()));
                redisTemplate.opsForValue().set(key, cached);
            }
        }
    }
}
