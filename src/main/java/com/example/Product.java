package com.example;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Objects;

public abstract class Product {
    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    protected Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.category = Objects.requireNonNull(category, "category cannot be null");
        setPriceInternal(price);
    }

    private void setPriceInternal(BigDecimal price) {
        if (price == null) {
            throw new NullPointerException("price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public UUID uuid() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    public void price(BigDecimal newPrice) {
        setPriceInternal(newPrice);
    }

    public abstract String productDetails();
}
