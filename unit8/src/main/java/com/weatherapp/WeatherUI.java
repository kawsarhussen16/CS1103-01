package com.weatherapp;

import java.awt.*;
import java.net.URI;
import java.util.List;
import javax.swing.*;

/**
 * WeatherUI
 *
 * This class represents the main graphical user interface (GUI)
 * of the Weather Information Application.
 *
 * It is responsible for:
 * - Accepting user input (city name)
 * - Displaying weather results
 * - Showing weather icons
 * - Displaying search history
 * - Connecting UI actions with backend services
 */
public class WeatherUI extends JFrame {

    // ===== USER INPUT COMPONENTS =====
    // Text field where user enters city name
    private JTextField cityField;

    // Text area used to display weather results in a readable format
    private JTextArea outputArea;

    // Dropdown menu for selecting temperature unit (Celsius/Fahrenheit)
    private JComboBox<String> unitBox;

    // Label used to display weather condition icon from API
    private JLabel iconLabel;

    // ===== SERVICE LAYER OBJECTS =====
    // Handles API calls and fetches weather data
    private final WeatherService weatherService;

    // Stores and retrieves search history
    private final HistoryManager historyManager;

    /**
     * Constructor
     *
     * Initializes backend services and builds the UI.
     */
    public WeatherUI() {

        // Create service objects once when UI starts
        weatherService = new WeatherService();
        historyManager = new HistoryManager();

        // Build all UI components and layout
        initializeUI();
    }

    /**
     * initializeUI()
     *
     * This method builds the entire GUI layout including:
     * - Window settings
     * - Input fields
     * - Buttons
     * - Panels and layout positioning
     * - Event listeners (button actions)
     */
    private void initializeUI() {

        // ===== WINDOW CONFIGURATION =====
        setTitle("Weather Information App"); // Window title
        setSize(800, 600); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app on close
        setLocationRelativeTo(null); // Center window on screen

        // ===== BACKGROUND PANEL =====
        // Custom background panel (likely contains image or gradient)
        JPanel background = BackgroundManager.getBackgroundPanel();

        // Use BorderLayout to organize UI sections (top, center, right, etc.)
        background.setLayout(new BorderLayout());

        // Set custom panel as main content pane
        setContentPane(background);

        // ===== INPUT COMPONENTS =====

        // City input field where user types city name
        cityField = new JTextField(15);

        // Button to trigger weather API request
        JButton searchButton = new JButton("Get Weather");

        // Button to display stored search history
        JButton historyButton = new JButton("View History");

        // Dropdown for selecting temperature unit
        unitBox = new JComboBox<>(new String[]{
            "Celsius",
            "Fahrenheit"
        });

        // Text area for showing formatted weather output
        outputArea = new JTextArea();
        outputArea.setEditable(false); // Prevent user editing results

        // Label for displaying weather icon (sun/cloud/rain etc.)
        iconLabel = new JLabel();

        // ===== TOP PANEL (INPUT AREA) =====
        // This panel holds all user input controls in a single row
        JPanel topPanel = new JPanel();

        // Add components in logical order: label → input → options → buttons
        topPanel.add(new JLabel("City:"));
        topPanel.add(cityField);
        topPanel.add(unitBox);
        topPanel.add(searchButton);
        topPanel.add(historyButton);

        // ===== LAYOUT PLACEMENT =====
        // NORTH = input panel at top
        background.add(topPanel, BorderLayout.NORTH);

        // CENTER = weather output display
        background.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // EAST = weather icon display
        background.add(iconLabel, BorderLayout.EAST);

        // Re-apply content pane (safe redundancy, ensures UI is set correctly)
        setContentPane(background);

        // ===== EVENT HANDLING =====
        // When user clicks "Get Weather", call searchWeather()
        searchButton.addActionListener(e -> searchWeather());

        // When user clicks "View History", show previous searches
        historyButton.addActionListener(e -> showHistory());
    }

    /**
     * searchWeather()
     *
     * This method:
     * 1. Reads user input (city name)
     * 2. Validates input
     * 3. Calls WeatherService API
     * 4. Converts temperature if needed
     * 5. Displays results in UI
     * 6. Loads weather icon
     */
    private void searchWeather() {

        // Get city name from input field
        String city = cityField.getText().trim();

        // Validate empty input
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a city name.");
            return;
        }

        try {
            // Fetch weather data from service layer (API call happens here)
            WeatherData data = weatherService.getWeather(city);

            // Save search term to history storage
            historyManager.addSearch(city);

            // Extract temperature from API response (default is Celsius)
            double temp = data.getTemperature();

            // Convert temperature if user selected Fahrenheit
            if ("Fahrenheit".equals(unitBox.getSelectedItem())) {
                temp = UnitConverter.celsiusToFahrenheit(temp);
            }

            // Display formatted weather information in text area
            outputArea.setText(
                    "City: " + data.getCity()
                    + "\nTemperature: " + String.format("%.2f", temp)
                    + "\nHumidity: " + data.getHumidity() + "%"
                    + "\nWind Speed: " + data.getWindSpeed() + " m/s"
                    + "\nCondition: " + data.getCondition()
            );

            // Load and display weather icon based on API icon code
            iconLabel.setIcon(loadIcon(data.getIconCode()));

        } catch (Exception ex) {
            // Show error dialog if API call or processing fails
            JOptionPane.showMessageDialog(this,
                    "Error retrieving weather data.\n" + ex.getMessage());
        }
    }

    /**
     * showHistory()
     *
     * This method retrieves previously searched cities
     * and displays them in a dialog box.
     */
    private void showHistory() {

        // Retrieve stored search history
        List<String> history = historyManager.getHistory();

        // If no history exists, inform the user
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No search history found.");
            return;
        }

        // Build a readable string of all history items
        StringBuilder sb = new StringBuilder();

        for (String item : history) {
            sb.append(item).append("\n");
        }

        // Display history in popup dialog
        JOptionPane.showMessageDialog(
                this,
                sb.toString(),
                "Search History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * loadIcon()
     *
     * This method loads weather icons from OpenWeatherMap CDN
     * using the icon code provided by the API.
     *
     * Example URL:
     * https://openweathermap.org/img/wn/10d@2x.png
     */
    private ImageIcon loadIcon(String iconCode) {

        try {
            // Build full URL for weather icon
            String url = "https://openweathermap.org/img/wn/"
                    + iconCode
                    + "@2x.png";

            // Convert URL into ImageIcon for Swing display
            return new ImageIcon(new URI(url).toURL());

        } catch (java.net.URISyntaxException | java.net.MalformedURLException e) {
            // Return null if icon cannot be loaded (fail-safe)
            return null;
        }
    }
}