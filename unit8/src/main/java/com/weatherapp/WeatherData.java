package com.weatherapp;

/**
 * WeatherData
 *
 * Model class representing current weather information.
 * This class follows encapsulation principles by keeping
 * variables private and exposing getters/setters.
 */
public class WeatherData {

    private String city;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private String condition;
    private String iconCode;

    /**
     * Default constructor.
     */
    public WeatherData() {
    }

    /**
     * Parameterized constructor.
     */
    public WeatherData(
            String city,
            double temperature,
            int humidity,
            double windSpeed,
            String condition,
            String iconCode) {

        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.condition = condition;
        this.iconCode = iconCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }
}