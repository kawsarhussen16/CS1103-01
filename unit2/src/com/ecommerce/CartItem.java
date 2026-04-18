package com.ecommerce;

// CartItem represents a product inside the shopping cart along with its quantity
public class CartItem {

    private final Product product;
    private final int quantity;

    // Constructor initializes cart item with product and quantity
    public CartItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.product = product;   // Assign product reference
        this.quantity = quantity;  // Assign quantity selected by customer
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    // Calculates total price for this cart item (price × quantity)
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
