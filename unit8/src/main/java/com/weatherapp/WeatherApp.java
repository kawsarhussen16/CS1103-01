package com.weatherapp;

import javax.swing.SwingUtilities;

/**
 * WeatherApp
 *
 * Main application entry point.
 * Launches the GUI on the Event Dispatch Thread (EDT),
 * which is the recommended approach for Swing applications.
 */
public class WeatherApp {

    /**
     * Program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            WeatherUI ui = new WeatherUI();
            ui.setVisible(true);
        });
    }
}