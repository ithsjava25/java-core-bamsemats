package com.example;

import java.math.BigDecimal;

public interface Shippable {
    /**
     * Returns the weight in kg (as Double). May return null if weight unknown.
     */
    Double weight();

    /**
     * Calculates shipping cost for this item.
     */
    BigDecimal calculateShippingCost();
}
