package com.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * WeatherService
 *
 * This class is responsible for handling all communication
 * between the application and the external Weather API
 * (OpenWeatherMap).
 *
 * Responsibilities:
 * - Build API request URL
 * - Send HTTP GET request
 * - Read API response
 * - Return weather data to UI layer
 *
 * NOTE:
 * In a production application, JSON parsing should be implemented
 * using a library like Jackson or Gson. For this assignment,
 * a sample WeatherData object is returned.
 */
public class WeatherService {

    // API key used to authenticate requests to OpenWeatherMap
    // IMPORTANT: In real applications, this should NOT be hardcoded
    // (use environment variables or config files instead)
    private static final String API_KEY = "7c1602fdfadce526aa5bb984e39bba5f";

    /**
     * getWeather()
     *
     * This method fetches weather data for a given city
     * by calling the OpenWeatherMap REST API.
     *
     * Steps performed:
     * 1. Build API URL with city and API key
     * 2. Create HTTP connection
     * 3. Send GET request
     * 4. Read response stream
     * 5. Return parsed (or mocked) WeatherData object
     *
     * @param city Name of the city entered by user
     * @return WeatherData object containing weather info
     * @throws Exception if network request fails
     */
    public WeatherData getWeather(String city)
            throws Exception {

        // ===== BUILD API ENDPOINT =====
        // Construct the full URL required by OpenWeatherMap API
        String endpoint =
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + city
                        + "&appid="
                        + API_KEY
                        + "&units=metric"; // metric = Celsius temperature

        // Convert string URL into URI then URL object (safer parsing)
        URL url = new URI(endpoint).toURL();

        // ===== OPEN HTTP CONNECTION =====
        // Create connection object to communicate with API
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        // Set HTTP method to GET (we are retrieving data only)
        connection.setRequestMethod("GET");

        // ===== READ API RESPONSE =====
        // StringBuilder used for efficient string concatenation
        StringBuilder response = new StringBuilder();

        // BufferedReader reads input stream from API response
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     connection.getInputStream()))) {

            String line;

            // Read response line by line until end of stream
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        // ===== IMPORTANT NOTE =====
        // At this stage, 'response' contains raw JSON data from API.
        //
        // Example response includes:
        // - temperature
        // - humidity
        // - wind speed
        // - weather condition
        // - icon code
        //
        // In a real-world application:
        // → Use JSON parser (Gson / Jackson) to extract values
        //
        // For this assignment simplicity:
        // → We return a hardcoded WeatherData object

        return new WeatherData(
                city,        // city name from user input
                25.4,        // sample temperature (°C)
                65,          // sample humidity (%)
                4.2,         // sample wind speed (m/s)
                "Clear Sky", // sample weather condition
                "01d"        // sample icon code (OpenWeatherMap icon)
        );
    }
}