package tcp_socket.ex2_primitive_types;

import java.net.*;

// Ex2 variant: the same calculator idea, but using binary primitive values.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the server socket.
            ServerSocket serverSocket = new ServerSocket(4447);

            // Step 2: keep listening for client connections.
            while (true) {
                // Step 3: accept one client socket.
                Socket socket = serverSocket.accept();

                // Step 4: start a new thread to handle that client.
                new ClientHandler(socket).start();
            }
        } catch (Exception exception) {
            System.out.println("TCP primitive server error: " + exception.getMessage());
        }
    }
}
