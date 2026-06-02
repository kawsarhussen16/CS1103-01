import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ChatServer coordinates distributed TCP connections, mints thread-safe unique
 * tracking IDs, and manages broad distribution of network text messages.
 * * It acts as a central hub: clients connect to it, and any message sent by one 
 * client is relayed (broadcast) to all currently connected clients.
 */
public class ChatServer {
    
    // The fixed port number the server will listen on for incoming client connections.
    private static final int PORT = 3000;
    
    // AtomicInteger provides lock-free, thread-safe operations on a single integer.
    // Since multiple client threads might connect simultaneously, this ensures no two 
    // users ever get assigned the same ID when idGenerator.getAndIncrement() is called.
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    
    // A thread-safe Set used to store the output streams (PrintWriters) of all active clients.
    // Using a Set prevents duplicate entries. Collections.synchronizedSet ensures that adding
    // or removing clients from multiple threads won't corrupt the data structure.
    private static final Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("[INFO] Chat Server initialized. Listening on port " + PORT + "...");
        
        // Use a try-with-resources block to instantiate the ServerSocket.
        // This guarantees the serverSocket will be automatically closed if the program crashes.
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
            // The server runs in an infinite loop, constantly listening for new connections.
            while (true) {
                // serverSocket.accept() blocks (halts execution of this main thread) 
                // until a new client attempts to connect. Once connected, it returns a Socket object.
                Socket clientSocket = serverSocket.accept();
                
                // Assign a unique ID to this new user and increment the counter safely.
                int userId = idGenerator.getAndIncrement();
                System.out.println("[CONNECT] User-" + userId + " allocated from address: " + clientSocket.getRemoteSocketAddress());

                // Hand off the connection socket to an isolated execution worker thread.
                // If we didn't use a new Thread here, the server would be stuck handling 
                // one user and wouldn't be able to accept any other connections.
                new Thread(new ClientHandler(clientSocket, userId)).start();
            }
        } catch (IOException e) {
            // Catches errors related to binding the server socket (e.g., port already in use)
            System.err.println("[CRITICAL] Server execution encountered terminal error: " + e.getMessage());
        }
    }

    /**
     * Iterates safely across registered connection streams to broadcast network payloads.
     * * @param message The string message to send to all connected clients.
     */
    public static void broadcast(String message) {
        // CRITICAL: Even though clientWriters is a synchronizedSet, iterating over it 
        // is NOT implicitly thread-safe. We must manually synchronize on the set itself 
        // to prevent a ConcurrentModificationException if a client joins or leaves 
        // exactly while we are looping through this list.
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }

    /**
     * Managed worker logic handling active session timelines for independent client pipes.
     * Implements Runnable so its instance can be executed by a separate Thread.
     */
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final int userId;
        private PrintWriter out; // The output stream used to send messages back to this specific client

        public ClientHandler(Socket socket, int userId) {
            this.socket = socket;
            this.userId = userId;
        }

        @Override
        public void run() {
            // Try-with-resources is used here for the BufferedReader.
            // It wraps the raw InputStream from the socket into a character-based reader.
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                // Initialize the PrintWriter. 
                // The 'true' argument enables auto-flushing, meaning messages are sent immediately 
                // when println() is called, rather than being buffered in memory.
                this.out = new PrintWriter(socket.getOutputStream(), true);
                
                // Register this client's writer into the global pool so they can receive broadcasts.
                clientWriters.add(this.out);

                // Notify everyone currently in the chat room that a new user joined.
                ChatServer.broadcast("[SERVER] User-" + userId + " entered the conversation workspace.");

                String message;
                
                // Continuously read lines from this client until they disconnect.
                // in.readLine() blocks until the client sends a message or closes their connection (returns null).
                while ((message = in.readLine()) != null) {
                    
                    // Allow the user to gracefully disconnect by typing "/exit"
                    if (message.equalsIgnoreCase("/exit")) {
                        break; // Breaks the loop, sending execution down to the 'finally' block
                    }
                    
                    // If it's a normal message, broadcast it to all users (including the sender).
                    ChatServer.broadcast("User-" + userId + ": " + message);
                }
            } catch (IOException e) {
                // Catches unexpected disconnects (e.g., client closed their terminal abruptly)
                System.out.println("[WARN] Connection disruption on track Node User-" + userId + ": " + e.getMessage());
            } finally {
                // The 'finally' block ensures that no matter how the client disconnects 
                // (gracefully via "/exit" or forcefully via crash), their resources are cleaned up.
                
                if (this.out != null) {
                    // Remove the client's output stream from the global set so we don't try 
                    // sending messages to a closed connection.
                    clientWriters.remove(this.out);
                    this.out.close();
                }
                try {
                    // Explicitly close the socket connection to free up system resources.
                    socket.close();
                } catch (IOException e) {
                    System.err.println("[ERROR] Failed resource cleanup for User-" + userId);
                }
                
                // Notify the remaining clients that this user has left.
                ChatServer.broadcast("[SERVER] User-" + userId + " terminated their session.");
                System.out.println("[DISCONNECT] User-" + userId + " cleared from worker register.");
            }
        }
    }
}