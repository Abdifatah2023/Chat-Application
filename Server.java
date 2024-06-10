
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 5555; // Port number for clients to connect
    private static int clientCount = 0; // Static variable to track client count

    private ServerSocket serverSocket;
    private ArrayList<Socket> clientSockets = new ArrayList<>();

    public Server() {
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT + ". Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);

                // Increment client count
                clientCount++;
                System.out.println("Client " + clientCount + " connected.");

                new ServerThread(clientSocket, clientCount).start();
            }

        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }

    private class ServerThread extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private int clientNumber;

        public ServerThread(Socket clientSocket, int clientNumber) {
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                while (true) {
                    String message = in.readLine();
                    if (message == null) {
                        break;
                    }

                    // Send message to all clients
                    for (Socket socket : clientSockets) {
                        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                        pw.println("client" + clientNumber + ": " + message);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                // Remove client from the list when disconnected
                clientSockets.remove(clientSocket);
                System.out.println("Client " + clientNumber + " disconnected.");
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
