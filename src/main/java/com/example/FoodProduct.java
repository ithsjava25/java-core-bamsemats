package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weightKg;

    public FoodProduct(UUID id,
                       String name,
                       Category category,
                       BigDecimal price,
                       LocalDate expirationDate,
                       BigDecimal weightKg) {
        super(id, name, category, price);
        this.expirationDate = Objects.requireNonNull(expirationDate, "expirationDate cannot be null");
        this.weightKg = Objects.requireNonNull(weightKg, "weight cannot be null");
        if (weightKg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
    }

    @Override
    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public Double weight() {
        return weightKg.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        // cost = weight * 50
        return weightKg.multiply(new BigDecimal("50"));
    }

    @Override
    public String productDetails() {
        // "Food: Milk, Expires: 2025-12-24"
        return String.format("Food: %s, Expires: %s", name(), expirationDate.toString());
    }
}
