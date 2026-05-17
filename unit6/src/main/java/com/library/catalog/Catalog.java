package com.library.catalog;

import com.library.exception.ItemNotFoundException;
import com.library.model.LibraryItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Catalog class to manage library items.
 * @param <T> any type that extends LibraryItem
 */
public class Catalog<T extends LibraryItem<?>> {

    // List to store library items
    private final List<T> items;

    /**
     * Constructor initializes an empty catalog.
     */
    public Catalog() {
        items = new ArrayList<>();
    }

    /**
     * Adds a new item to the catalog.
     */
    public void addItem(T item) {
        items.add(item);
        System.out.println("Item added successfully.");
    }

    /**
     * Removes an item by its ID.
     * Throws exception if item does not exist.
     */
    public void removeItem(Object itemID) throws ItemNotFoundException {

        // removeIf returns true if any element was removed
        boolean removed = items.removeIf(item -> item.getItemID().equals(itemID));

        if (!removed) {
            // Throw custom exception if item not found
            throw new ItemNotFoundException("Item with ID " + itemID + " not found.");
        } else {
            System.out.println("Item removed successfully.");
        }
    }

    /**
     * Retrieves an item by ID.
     */
    public T getItem(Object itemID) throws ItemNotFoundException {

        // Loop through items to find match
        for (T item : items) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }

        // If not found, throw exception
        throw new ItemNotFoundException("Item not found.");
    }

    /**
     * Displays all items in the catalog.
     */
    public void displayCatalog() {

        if (items.isEmpty()) {
            System.out.println("Catalog is empty.");
            return;
        }

        // Print each item
        for (T item : items) {
            System.out.println(item);
        }
    }
}