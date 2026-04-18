package com.ecommerce.orders;

// Enum represents a fixed set of constants for order status
// Using enum ensures only valid order states are allowed (type-safe)
public enum OrderStatus {
    PLACED,
    SHIPPED,
    DELIVERED
}
