# Online Chat Application — Documentation & Architecture Report

## 1. Project Overview
This project is a multi-user, real-time online chat application built using Java's network socket programming interface (`java.net`) and concurrent execution frameworks. The application adopts a centralized **Client-Server Architecture** designed to support multiple, concurrent bidirectional communication channels.

The primary objective is to demonstrate core concepts of low-level networking, thread safety, I/O multiplexing, and robust state synchronization in a distributed environment.

---

## 2. System Architecture & Design
The system topology relies on a single central hub (`ChatServer`) acting as a broker, routing payloads across arbitrary numbers of isolated client nodes (`ChatClient`).

### 2.1 Communication Protocol
- **Transport Layer:** TCP (Transmission Control Protocol) is selected to guarantee reliable, ordered, and error-checked delivery of character streams.
- **Application Layer Frame:** Plaintext lines terminated by a standard newline sequence (`\n`). 
- **Control Signal:** A special control token (`/exit`) is monitored inline to trigger deterministic teardown sequences for active sessions.

### 2.2 Concurrency Model
Because network operations (`Socket.accept()`, `BufferedReader.readLine()`) are blocking calls, a single-threaded execution context would lock up, leading to starvation of other nodes. This architecture uses a dedicated **Thread-per-Client Pattern** on the server side and an **Asynchronous Reader-Writer Split** on the client side.


       +---------------------------------------------+
       |                  ChatServer                 |
       |  +---------------------------------------+  |
       |  |  ServerSocket.accept() (Main Thread)  |  |
       +--+-------------------+---------------+------+
                              |
                 Spawns on client connection
                              |
            +-----------------+-----------------+
            |                                   |
            v                                   v
+-----------------------+           +-----------------------+
|  ClientHandler (ID:1) |           |  ClientHandler (ID:2) |
|                       |           |                       |
|  - Shared Writer Set  |           |  - Shared Writer Set  |
|  - Blocks on readLine |           |  - Blocks on readLine |
+-----------+-----------+           +-----------+-----------+
            ^                                   ^
            |  TCP Connection                   |  TCP Connection
            v                                   v
+-----------------------+           +-----------------------+
|       ChatClient      |           |       ChatClient      |
|                       |           |                       |
|  - Main Thread (Write)|           |  - Main Thread (Write)|
|  - IncomingReader Th. |           |  - IncomingReader Th. |
+-----------------------+           +-----------------------+

## 3. Detailed Component Breakdown
### 3.1 ChatServer & ClientHandler
The server pipeline works through two nested operational loops:

The Lifecycle Loop (Main Thread): Continuously listens on port 3000. Upon accepting a connection via serverSocket.accept(), it updates a thread-safe global counter (AtomicInteger) to mint a unique User-ID, and provisions a ClientHandler thread.

The Message Broadcast Loop (Worker Threads): Each worker manages an active client socket. It registers the client's PrintWriter stream into a thread-safe reference collection (Collections.synchronizedSet). It then blocks on stream reads, immediately propagating any arriving payloads to all active references using synchronized block primitives.

### 3.2 ChatClient & IncomingReader
To prevent user terminal input from blocking incoming network transmissions, the client divides its responsibilities:

Outbound Write Pipe (Main Thread): Captures native OS console keystrokes via System.stdin, framing messages and flushing them down the socket pipe.

Inbound Read Pipe (IncomingReader Thread): Runs an isolated execution loop that blocks on socket.getInputStream(). When a line drops from the server, it outputs it to the console instantly.

## 4. Execution and Verification Guide
### 4.1 Compilation Phase
To transform source classes into Java bytecode execute the standard compiler toolchain within a unified directory shell:


Bash
run javac ChatServer.java ChatClient.java
### 4.2 Launch Configuration Sequence


Start Infrastructure Engine (Terminal 1):

Bash
run java ChatServer
Expected output trace: [INFO] Chat Server initialized. Listening on port 3000...

Initialize Node Session 1 (Terminal 2):
Bash
run java ChatClient


Initialize Node Session 2 (Terminal 3):
Bash
run java ChatClient