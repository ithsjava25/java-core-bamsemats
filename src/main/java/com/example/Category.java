package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Constructor;

public final class Category {
    // Flyweight cache
    private static final Map<String, Category> CACHE = new ConcurrentHashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    /**
     * Factory method with validation and normalization.
     * @param name category name
     * @return canonical Category instance
     * @throws NullPointerException or IllegalArgumentException depending on name
     */
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        // Normalize: capitalize first letter, rest lowercase
        String normalized = capitalize(trimmed);
        return CACHE.computeIfAbsent(normalized, Category::new);
    }

    private static String capitalize(String s) {
        if (s.isEmpty()) return s;
        String lower = s.toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    public String getName() {
        return name;
    }

    // equals/hashCode to ensure correct behavior in maps/sets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category other = (Category) o;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Category{" + name + "}";
    }
}
