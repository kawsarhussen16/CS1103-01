package com.ecommerce;

// Custom exception class for handling invalid order scenarios
// Extends Exception → makes it a checked exception (must be handled or declared)
public class InvalidOrderException extends Exception {

    // Constructor that accepts a custom error message
    public InvalidOrderException(String message) {

        // Passes the message to the parent Exception class
        // This allows us to retrieve the message using getMessage()
        super(message);
    }
}
