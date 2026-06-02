package com.weatherapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * HistoryManager
 *
 * This class is responsible for managing the search history
 * of the Weather Application.
 *
 * It provides functionality to:
 * - Store searched city names in memory
 * - Persist search history to a local file (history.txt)
 * - Load previous history when the application starts
 *
 * Each history entry includes:
 * - City name
 * - Timestamp of the search
 */
public class HistoryManager {

    // In-memory list that stores search history for current session
    private final List<String> history;

    // File used to persist search history between application runs
    private static final String FILE_NAME = "history.txt";

    /**
     * Constructor
     *
     * Initializes the history list and loads previously saved
     * search history from file storage.
     */
    public HistoryManager() {
        history = new ArrayList<>();

        // Load previous history from file when application starts
        loadHistoryFromFile();
    }

    /**
     * addSearch()
     *
     * Adds a new city search entry to:
     * 1. In-memory list (for current session use)
     * 2. File storage (for permanent persistence)
     *
     * Each entry includes:
     * - City name
     * - Timestamp of when the search was performed
     *
     * @param city The city searched by the user
     */
    public void addSearch(String city) {

        // Generate current timestamp in readable format
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Combine city and timestamp into a single record string
        String record = city + " - " + timestamp;

        // Store in memory list
        history.add(record);

        // ===== FILE PERSISTENCE =====
        // Append new record to history.txt file so data is not lost
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_NAME, true))) {

            writer.println(record);

        } catch (IOException e) {
            // Handle file write errors gracefully
            System.out.println("Error saving history: " + e.getMessage());
        }
    }

    /**
     * getHistory()
     *
     * Returns the full list of search history entries
     * stored in memory during the application runtime.
     *
     * @return List of city search records
     */
    public List<String> getHistory() {
        return history;
    }

    /**
     * loadHistoryFromFile()
     *
     * This method is executed at application startup.
     *
     * It reads previously saved search history from a file
     * and loads it into memory so that past searches
     * are available in the current session.
     *
     * If the file does not exist, a new history file will
     * be created automatically when a search is added.
     */
    public final void loadHistoryFromFile() {

        // Reference to history file on disk
        File file = new File(FILE_NAME);

        // If no file exists, no previous history is available
        if (!file.exists()) {
            System.out.println("No history file found. Creating new one...");
            return;
        }

        // Read file content line by line
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            // Load each saved record into memory list
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }

        } catch (IOException e) {
            // Handle file read errors gracefully
            System.out.println("Error loading history: " + e.getMessage());
        }
    }
}