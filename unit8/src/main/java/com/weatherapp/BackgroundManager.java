package com.weatherapp;

import java.awt.*;
import java.time.LocalTime;
import javax.swing.*;

/**
 * BackgroundManager
 *
 * This class is responsible for creating a custom JPanel
 * that displays a dynamic background image based on the time of day.
 *
 * It enhances the UI experience by changing the background theme:
 * - Morning → Bright daylight image
 * - Afternoon → Warm daylight image
 * - Evening/Night → Dark/night-themed image
 *
 * It uses Swing's paintComponent method to render the image
 * properly across the entire window.
 */
public class BackgroundManager {

    /**
     * getBackgroundPanel()
     *
     * Creates and returns a JPanel with a dynamic background image.
     *
     * The selected image depends on the current system time.
     *
     * @return JPanel with custom-painted background image
     */
    public static JPanel getBackgroundPanel() {

        // Variable to hold selected background image URL
        String imageUrl;

        // Get current system time
        LocalTime time = LocalTime.now();

        // ===== TIME-BASED BACKGROUND SELECTION =====
        // Morning: before 12:00 PM → bright sky image
        if (time.isBefore(LocalTime.NOON)) {
            imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb";

        // Afternoon: before 6:00 PM → warm daylight image
        } else if (time.isBefore(LocalTime.of(18, 0))) {
            imageUrl = "https://images.unsplash.com/photo-1527766833261-b09c3163a791";

        // Evening/Night: after 6:00 PM → darker/night image
        } else {
            imageUrl = "https://images.unsplash.com/photo-1504384308090-c894fdcc538d";
        }

        // Load image from URL into Swing-compatible ImageIcon
        ImageIcon icon = new ImageIcon(imageUrl);

        // Extract Image object for drawing
        Image img = icon.getImage();

        /**
         * Anonymous JPanel subclass
         *
         * We override paintComponent to manually draw the background image.
         * This ensures the image scales properly with window size.
         */
        return new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw image stretched to fill entire panel area
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
}