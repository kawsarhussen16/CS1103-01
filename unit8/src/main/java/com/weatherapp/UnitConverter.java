package com.weatherapp;

/**
 * UnitConverter
 *
 * Utility class responsible for temperature
 * and wind speed conversions.
 */
public class UnitConverter {

    /**
     * Converts Celsius to Fahrenheit.
     *
     * Formula:
     * F = (C × 9/5) + 32
     *
     * @param celsius temperature in Celsius
     * @return Fahrenheit value
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    /**
     * Converts Fahrenheit to Celsius.
     *
     * @param fahrenheit temperature in Fahrenheit
     * @return Celsius value
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    /**
     * Converts meters per second to miles per hour.
     *
     * @param metersPerSecond wind speed in m/s
     * @return mph value
     */
    public static double metersPerSecondToMPH(double metersPerSecond) {
        return metersPerSecond * 2.23694;
    }

    /**
     * Converts miles per hour to meters per second.
     *
     * @param mph wind speed in mph
     * @return m/s value
     */
    public static double mphToMetersPerSecond(double mph) {
        return mph / 2.23694;
    }
}