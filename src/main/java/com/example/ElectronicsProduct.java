package com.example;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weightKg;

    public ElectronicsProduct(UUID id,
                              String name,
                              Category category,
                              BigDecimal price,
                              int warrantyMonths,
                              BigDecimal weightKg) {
        super(id, name, category, price);
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        this.warrantyMonths = warrantyMonths;
        this.weightKg = Objects.requireNonNull(weightKg, "weight cannot be null");
    }

    public int warrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public Double weight() {
        return weightKg.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal cost = new BigDecimal("79");
        if (weightKg.doubleValue() > 5.0) {
            cost = cost.add(new BigDecimal("49"));
        }
        return cost;
    }

    @Override
    public String productDetails() {
        // "Electronics: Laptop, Warranty: 24 months"
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyMonths);
    }
}
