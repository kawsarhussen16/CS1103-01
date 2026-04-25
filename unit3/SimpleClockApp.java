
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clock class responsible for continuously updating and displaying the current
 * time and date using multithreading.
 *
 * This class demonstrates: - Java Threads - Thread priorities - Shared memory
 * between threads (volatile keyword)
 */
class Clock {

    // Shared variable between threads.
    // 'volatile' ensures all threads always read the latest value.
    private volatile String currentTime;

    // Formatter for readable time output in HH:mm:ss dd-MM-yyyy format
    private final SimpleDateFormat formatter
            = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    /**
     * Starts the full clock system. This is the single entry point for
     * initializing both threads.
     */
    public void startClock() {
        startUpdaterThread();
        startDisplayThread();
    }

    /**
     * Background thread responsible for continuously updating the current time.
     * Runs independently from the display thread.
     */
    private void startUpdaterThread() {

        @SuppressWarnings("BusyWait")
        Thread updaterThread = new Thread(() -> {

            // Infinite loop keeps updating the time every second
            while (true) {
                try {
                    // Get current system time
                    Date now = new Date();

                    // Format and store shared time value
                    currentTime = formatter.format(now);

                    // Pause for 1 second before next update
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    // Handles thread interruption safely
                    System.out.println("Updater thread interrupted.");
                    break;
                }
            }
        });

        updaterThread.setName("Clock-Updater");

        // Lower priority because it is a background updating task
        updaterThread.setPriority(Thread.MIN_PRIORITY);

        updaterThread.start();
    }

    /**
     * Thread responsible for continuously displaying the current time. Reads
     * the shared variable updated by the updater thread.
     */
    private void startDisplayThread() {

        @SuppressWarnings("BusyWait")
        Thread displayThread = new Thread(() -> {

            // Infinite loop ensures continuous display updates
            while (true) {
                try {

                    // Display time only after it has been initialized
                    if (currentTime != null) {
                        System.out.println("Current Time: " + currentTime);
                    } else {
                        System.out.println("Initializing clock...");
                    }

                    // Refresh display every second
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    // Handles interruption safely
                    System.out.println("Display thread interrupted.");
                    break;
                }
            }
        });

        displayThread.setName("Clock-Display");

        // Higher priority to ensure smoother and more responsive display updates
        displayThread.setPriority(Thread.MAX_PRIORITY);

        displayThread.start();
    }
}

/**
 * Main class to run the Clock Application.
 */
public class SimpleClockApp {

    public static void main(String[] args) {

        // Create Clock instance
        Clock clock = new Clock();

        // Start multithreaded clock system
        clock.startClock();
    }
}
