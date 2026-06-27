package tcp_socket.example_thread;

import java.net.*;

// ExampleThread: threaded echo server that can accept many TCP clients.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the server socket.
            ServerSocket serverSocket = new ServerSocket(8001);

            // Step 2: keep listening for new clients.
            while (true) {
                // Step 3: accept one client socket.
                Socket clientSocket = serverSocket.accept();

                // Step 4: start a new thread to handle that client.
                new ClientHandler(clientSocket).start();
            }
        } catch (Exception exception) {
            System.out.println("Threaded TCP server error: " + exception.getMessage());
        }
    }
}
