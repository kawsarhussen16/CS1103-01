package com.library.ui;

import com.library.catalog.Catalog;
import com.library.exception.ItemNotFoundException;
import com.library.model.LibraryItem;
import java.util.Scanner;

/**
 * Main class providing command-line interface for the catalog.
 */
public class Main {

    public static void main(String[] args) {

        // Create catalog with Integer ID type
        Catalog<LibraryItem<Integer>> catalog = new Catalog<>();

        // Scanner for user input
        try (Scanner scanner = new Scanner(System.in)) {

            // Infinite loop for menu
            while (true) {

                // Display menu options
                System.out.println("\n--- Library Catalog Menu ---");
                System.out.println("1. Add Item");
                System.out.println("2. Remove Item");
                System.out.println("3. View Catalog");
                System.out.println("4. Get Item Details");
                System.out.println("5. Exit");

                System.out.print("Choose option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                try {
                    switch (choice) {
                        case 1 -> {
                            // Add new item
                            System.out.print("Enter ID: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Enter Title: ");
                            String title = scanner.nextLine();

                            System.out.print("Enter Author: ");
                            String author = scanner.nextLine();

                            catalog.addItem(new LibraryItem<>(id, title, author));
                        }
                        case 2 -> {
                            // Remove item
                            System.out.print("Enter ID to remove: ");
                            int removeId = scanner.nextInt();

                            catalog.removeItem(removeId);
                        }
                        case 3 -> catalog.displayCatalog();
                        case 4 -> {
                            // Retrieve item
                            System.out.print("Enter ID: ");
                            int searchId = scanner.nextInt();

                            System.out.println(catalog.getItem(searchId));
                        }
                        case 5 -> {
                            // Exit program
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid option.");
                    }

                } catch (ItemNotFoundException e) {
                    // Handle custom exception gracefully
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}