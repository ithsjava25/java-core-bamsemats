package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();
    private final String name;
    private final LinkedHashMap<UUID, Product> products = new LinkedHashMap<>();
    private final Set<UUID> changed = new LinkedHashSet<>();

    private Warehouse(String name) {
        this.name = name;
    }

    public static synchronized Warehouse getInstance(String name) {
        return INSTANCES.computeIfAbsent(name, Warehouse::new);
    }

    public String name() {
        return name;
    }

    public synchronized void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        products.put(product.uuid(), product);
    }

    public synchronized boolean isEmpty() {
        return products.isEmpty();
    }

    public synchronized List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public synchronized Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public synchronized void updateProductPrice(UUID id, BigDecimal newPrice) {
        Product p = products.get(id);
        if (p == null) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }
        p.price(newPrice);
        changed.add(id);
    }

    public synchronized List<Product> getChangedProducts() {
        return changed.stream()
                .map(products::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public synchronized List<Perishable> expiredProducts() {
        List<Perishable> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p instanceof Perishable per && per.isExpired()) {
                result.add(per);
            }
        }
        return result;
    }

    public synchronized List<Shippable> shippableProducts() {
        List<Shippable> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p instanceof Shippable s) {
                result.add(s);
            }
        }
        return result;
    }

    public synchronized void remove(UUID id) {
        products.remove(id);
        changed.remove(id);
    }

    public synchronized void clearProducts() {
        products.clear();
        changed.clear();
    }

    /**
     * Extra method verified by BasicTest: grouping by categories.
     */
    public synchronized Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(Product::category,
                        LinkedHashMap::new, Collectors.toList()));
    }
}
