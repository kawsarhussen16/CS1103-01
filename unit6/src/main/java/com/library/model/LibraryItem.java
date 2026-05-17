package com.library.model;

/**
 * Generic class representing a library item.
 * @param <T> the type of itemID (e.g., Integer, String)
 */
public class LibraryItem<T> {

    // Unique identifier for the item
    private final T itemID;

    // Title of the item (book, DVD, etc.)
    private final String title;

    // Author or creator of the item
    private final String author;

    /**
     * Constructor to initialize a library item.
     */
    public LibraryItem(T itemID, String title, String author) {
        this.itemID = itemID;
        this.title = title;
        this.author = author;
    }

    /**
     * Returns the item ID.
     */
    public T getItemID() {
        return itemID;
    }

    /**
     * Returns the title of the item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the item.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns a formatted string representation of the item.
     */
    @Override
    public String toString() {
        return "ID: " + itemID + ", Title: " + title + ", Author: " + author;
    }
}