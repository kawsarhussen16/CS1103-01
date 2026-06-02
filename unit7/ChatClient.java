import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ChatClient connects to a remote socket server, initializes an independent reader
 * thread to handle inbound streams, and utilizes the main thread to handle outbound streams.
 * * * The core architecture here is a "Two-Thread Design". 
 * One thread (Main) handles typing and sending messages to the server.
 * The other thread (IncomingReader) listens for and displays messages from the server.
 */
public class ChatClient {
    
    // The IP address and port of the ChatServer. "localhost" means the server 
    // is running on the exact same machine as this client.
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 3000;

    public static void main(String[] args) {
        System.out.println("[INIT] Connecting to chat runtime environment at " + SERVER_ADDRESS + ":" + SERVER_PORT + "...");
        
        // This is a complex try-with-resources block managing three critical resources.
        // If the program crashes, or if the user types "/exit" and the try block finishes,
        // Java will automatically and safely close the Socket, the consoleInput, and the PrintWriter.
        try (
            // 1. Establish the TCP connection to the server.
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            
            // 2. Wrap System.in (the local keyboard input) into a BufferedReader so we can read full lines of text.
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            
            // 3. Create the output stream to push text up to the server.
            // 'true' turns on auto-flushing, ensuring messages are sent across the network immediately.
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("[SUCCESS] Session established. Terminal ready for messages. Type '/exit' to drop session.\n");

            // Fork the background reader thread to monitor network input lines.
            // CRITICAL: We MUST do this in a new thread. If we didn't, the program would 
            // pause forever waiting for the user to type something, and would never be able 
            // to print incoming messages from other users.
            Thread readThread = new Thread(new IncomingReader(socket));
            readThread.start();

            // Main context loops over local platform console interactions.
            // This loop runs entirely on the Main thread.
            String userInput;
            
            // consoleInput.readLine() blocks until the user presses 'Enter' on their keyboard.
            while ((userInput = consoleInput.readLine()) != null) {
                
                // Immediately push the user's typed string out to the server.
                out.println(userInput);
                
                // If the user types /exit, we break out of the while loop.
                // Breaking the loop causes the code to reach the end of the try-with-resources block,
                // which automatically closes the socket and disconnects from the server.
                if (userInput.equalsIgnoreCase("/exit")) {
                    break;
                }
            }
            System.out.println("[SHUTDOWN] Exiting local workspace...");

        } catch (IOException e) {
            // This catches connection refusals (e.g., if the ChatServer isn't running yet)
            // or if the network connection drops abruptly while chatting.
            System.err.println("[FAILURE] Core client layer dropped connection or cannot reach server. Verify ChatServer is up.");
        }
    }

    /**
     * Read routine that intercepts incoming transmission payloads without context stalling.
     * Runs continuously in the background, completely independent of the user typing.
     */
    private static class IncomingReader implements Runnable {
        private final Socket socket;

        public IncomingReader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // Wrap the socket's raw inbound byte stream into a character-based BufferedReader.
            // Notice this is inside its own standard try-with-resources block.
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String serverMessage;
                
                // in.readLine() blocks this specific background thread until the server sends a message.
                // It returns null when the server cleanly closes the connection.
                while ((serverMessage = in.readLine()) != null) {
                    // Print the broadcasted message to the local user's screen.
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                // IMPORTANT BEHAVIOR: When the user types "/exit" in the Main thread, the Main thread 
                // closes the Socket. Because this background thread is likely currently stuck waiting 
                // at in.readLine(), closing the socket out from under it causes an intentional IOException.
                // We catch it here to allow this thread to shut down quietly without throwing a nasty stack trace.
                System.out.println("[INFO] Local reader loop closed down successfully.");
            }
        }
    }
}