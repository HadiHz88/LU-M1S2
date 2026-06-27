package tcp_socket.example;

import java.io.*;
import java.net.*;

// Example: classic single-client echo server using line-based TCP I/O.
public class Server {
    public static void main(String[] args) throws IOException {
        try {
            // Step 1: create the server socket on port 8000.
            ServerSocket serverSocket = new ServerSocket(8000);

            // Step 2: accept one client connection.
            Socket clientSocket = serverSocket.accept();

            // Step 3: create a reader to receive the client message.
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Step 4: create an output stream to send the reply.
            DataOutputStream toClient = new DataOutputStream(clientSocket.getOutputStream());

            // Step 5: read one line from the client.
            String message = fromClient.readLine();

            // Step 6: send the same message back to the client.
            toClient.writeBytes(message + "\n");

            // Step 7: close the opened sockets.
            clientSocket.close();
            serverSocket.close();
        } catch (IOException exception) {
            System.out.println("TCP example server error: " + exception.getMessage());
        }
    }
}
