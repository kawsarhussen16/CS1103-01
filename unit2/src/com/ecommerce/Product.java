package com.ecommerce;

// Product class represents an item that can be purchased in the store
public class Product {

    // Unique identifier for the product (final → cannot change after creation)
    private final int productID;

    // Name of the product (final → immutable once set)
    private final String name;

    // Price of the product (final → ensures price consistency)
    private final double price;

    // Available stock (can change when items are purchased)
    private int stock;

    // Constructor to initialize product details
    public Product(int productID, String name, double price, int stock) {
        // Validate input parameters
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.productID = productID; // Assign product ID
        this.name = name;           // Assign product name
        this.price = price;         // Assign product price
        this.stock = stock;         // Assign available stock
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Method to reduce stock when a product is purchased
    public boolean reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    // Method to display product details
    public void displayProduct() {
        System.out.println(productID + " - " + name + " ($" + price + ") Stock: " + stock);
    }
}
