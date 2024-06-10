
---

# Chat Application

## Overview
This project implements a simple chat application using Java Swing for the client-side GUI and a multi-threaded server using Java Sockets. It is composed of two main components: `Server` (the server) and `Client` (the client).

## Author
- Cabdifataax Maxamuud

## Features
- Clients can connect to a server and send messages.
- The server broadcasts messages from any client to all connected clients.
- Simple GUI for client interaction.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed.
- Basic understanding of Java and networking.

### Running the Server
1. Compile the `Server.java` file:
    ```sh
    javac Server.java
    ```
2. Run the server:
    ```sh
    java Server
    ```
3. The server will start on port `5555` and wait for clients to connect.

### Running the Client
1. Compile the `Client.java` file:
    ```sh
    javac Client.java
    ```
2. Run the client:
    ```sh
    java Client
    ```
3. A GUI will appear prompting for the server's hostname and port. By default, it is set to `localhost` and `5555`.
4. Click `Connect` to connect to the server. After connecting, you can type messages and send them by clicking the `Send` button.

## Code Explanation

### Server

The server code listens for client connections on port `5555`. When a client connects, it starts a new thread (`ServerThread`) to handle the client's communication.

- **ServerSocket**: Listens for incoming connections.
- **ArrayList<Socket> clientSockets**: Keeps track of all connected clients.
- **clientCount**: Keeps track of the number of clients connected.

Each `ServerThread`:
- Reads messages from the client.
- Broadcasts received messages to all connected clients.

### Client

The client code creates a GUI for user interaction and connects to the server using a socket.

- **Socket**: Used for client-server communication.
- **BufferedReader**: Reads messages from the server.
- **PrintWriter**: Sends messages to the server.
- **JTextArea**: Displays chat messages.
- **JTextField**: Allows users to type messages and server details.
- **JButton**: Handles connect/disconnect and send actions.

## Example Usage

1. **Start the server**:
   - Open a terminal and navigate to the directory containing `Server.java`.
   - Run the server as described in the Running the Server section.

2. **Start the client**:
   - Open a new terminal and navigate to the directory containing `Client.java`.
   - Run the client as described in the Running the Client section.
   - Repeat to start multiple clients.

3. **Chat**:
   - Type a message in the client's text field and press `Send`.
   - The message will be broadcast to all connected clients, including the sender.

## Troubleshooting

- **Connection Issues**: Ensure the server is running and the hostname/port are correct.
- **Port Conflicts**: Make sure port `5555` is not being used by another application.
- **Firewall Issues**: Ensure your firewall allows connections on the specified port.

## Future Improvements

- Enhance the GUI for a better user experience.
- Add user authentication.
- Implement message encryption for secure communication.
- Handle server shutdown and restart more gracefully.

---
