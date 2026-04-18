package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final int customerID;
    private final String name;
    private final List<CartItem> cart;

    private static final double DISCOUNT_RATE = 0.10;
    private static final double DISCOUNT_THRESHOLD = 1000;

    // Constructor initializes customer details and creates an empty cart
    public Customer(int customerID, String name) {
        this.customerID = customerID;  // Assign customer ID
        this.name = name;              // Assign customer name
        this.cart = new ArrayList<>(); // Initialize empty shopping cart
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    // Method to add products to cart with quantity validation
    public void addToCart(Product product, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Check if requested quantity is available in stock
        if (quantity <= product.getStock()) {

            // Add product to cart as a CartItem (product + quantity)
            cart.add(new CartItem(product, quantity));

            System.out.println(product.getName() + " added (" + quantity + ")");
            // Reduce stock of the product after adding to cart
            product.reduceStock(quantity);

        } else {
            System.out.println("Not enough stock!");
        }
    }

    // Method to calculate total price of all cart items
    public double calculateTotal() {

        double total = 0;

        // Loop through all items in cart
        for (CartItem item : cart) {

            // Add each item's total price (price × quantity)
            total += item.getTotalPrice();
        }

        // Apply discount logic before returning final amount
        return applyDiscount(total);
    }

    // Private method for discount logic (hidden from outside classes)
    private double applyDiscount(double total) {

        // Apply x(%) discount if total exceeds threshold
        if (total > DISCOUNT_THRESHOLD) {
            return total * (1 - DISCOUNT_RATE);
        }

        // Otherwise return original total
        return total;
    }

    // Returns the full cart list (used for order creation)
    public List<CartItem> getCart() {
        return cart;
    }

    // Displays cart items and total cost
    public void viewCart() {

        System.out.println("\nCart:");

        // Loop through cart items and display details
        for (CartItem item : cart) {

            System.out.println(
                    item.getProduct().getName()
                    + " x" + item.getQuantity()
                    + " = $" + item.getTotalPrice()
            );
        }

        // Show final total after discount
        System.out.println("Total (after discount): $" + calculateTotal());
    }

    // Validates whether order can be placed
    public void validateOrder() throws InvalidOrderException {

        // If cart is empty, throw custom exception
        if (cart.isEmpty()) {
            throw new InvalidOrderException("Cart is empty!");
        }
    }
}
