package com.ecommerce.orders;

// Importing required classes from other packages
import com.ecommerce.CartItem;
import com.ecommerce.Customer;
import java.util.List;

// Order class represents a completed purchase made by a customer
public class Order {

    private final int orderID;
    private final double total;
    private final Customer customer;
    private final List<CartItem> items;
    private OrderStatus status;

    // Constructor to initialize an order
    public Order(int orderID, Customer customer, List<CartItem> items) {
        this.orderID = orderID;              // Assign order ID
        this.customer = customer;            // Assign customer
        this.items = List.copyOf(items);     // Assign IMMUTABLE list of cart items

        // Calculate total cost of all items in the order
        this.total = calculateTotal();

        // Default status when order is created
        this.status = OrderStatus.PLACED;
    }

    // Private method to calculate total order cost
    // Only used internally by the Order class
    private double calculateTotal() {
        double sum = 0;

        for (CartItem item : items) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

    // Method to update order status (e.g., PLACED → SHIPPED → DELIVERED)
    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    // Generates a formatted summary of the order
    public String generateSummary() {

        // StringBuilder is used for efficient string concatenation
        StringBuilder sb = new StringBuilder();

        // Header section
        sb.append("\n===== ORDER SUMMARY =====\n");

        // Basic order details
        sb.append("Order ID: ").append(orderID).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");

        // Loop through each item and display details
        for (CartItem item : items) {
            sb.append(item.getProduct().getName()) // Product name
                    .append(" x").append(item.getQuantity()) // Quantity
                    .append(" = $").append(item.getTotalPrice()) // Total price for item
                    .append("\n");
        }

        // Display total order price
        sb.append("Total: $").append(total).append("\n");

        // Display current order status
        sb.append("Status: ").append(status);

        // Return the complete formatted summary
        return sb.toString();
    }
}
