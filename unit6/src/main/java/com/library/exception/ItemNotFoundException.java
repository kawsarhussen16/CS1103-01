package com.library.exception;

/**
 * Custom exception thrown when a library item is not found.
 */
public class ItemNotFoundException extends Exception {

    /**
     * Constructor accepts custom error message.
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}