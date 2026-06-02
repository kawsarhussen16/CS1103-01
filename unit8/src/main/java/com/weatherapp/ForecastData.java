package com.weatherapp;

/**
 * ForecastData
 *
 * This class is a simple data model (POJO - Plain Old Java Object)
 * used to represent weather forecast information for a specific day.
 *
 * It holds structured weather data returned from a weather service,
 * typically for a future date in a multi-day forecast feature.
 *
 * Responsibilities:
 * - Store forecast date
 * - Store predicted temperature
 * - Store weather condition (e.g., Rain, Clear, Clouds)
 *
 * This class does NOT contain any logic.
 * It is purely used for transporting and storing data.
 */
public class ForecastData {

    // Date for which the forecast applies (e.g., "2026-06-02")
    private final String date;

    // Predicted temperature for that date (in Celsius or Fahrenheit depending on app setting)
    private final double temperature;

    // Weather condition description (e.g., "Clear Sky", "Rain", "Cloudy")
    private final String condition;

    /**
     * Constructor
     *
     * Initializes a ForecastData object with required weather details.
     *
     * @param date Date of the forecast
     * @param temperature Predicted temperature value
     * @param condition Weather condition description
     */
    public ForecastData(String date,
                        double temperature,
                        String condition) {

        this.date = date;
        this.temperature = temperature;
        this.condition = condition;
    }

    /**
     * Returns the forecast date.
     *
     * @return date in String format
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the predicted temperature.
     *
     * @return temperature value as double
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Returns the weather condition description.
     *
     * @return condition (e.g., "Sunny", "Rainy")
     */
    public String getCondition() {
        return condition;
    }
}