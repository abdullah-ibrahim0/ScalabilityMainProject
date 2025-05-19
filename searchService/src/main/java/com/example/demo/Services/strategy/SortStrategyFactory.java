package com.example.SearchService.services.strategy;


public class SortStrategyFactory {
    public static SortStrategy get(String sortKey) {
        if (sortKey == null || sortKey.isEmpty()) {
            return products -> products; // no sort
        }
        return switch (sortKey) {
            case "price:asc" -> new PriceAscSort();
            case "price:desc" -> new PriceDescSort();
            default -> products -> products; // no sort
        };
    }
}
