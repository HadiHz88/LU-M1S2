package tcp_socket.ex2_buffered_reader;

import java.net.*;

// Ex2: multi-client calculator using BufferedReader/PrintWriter text messages.
public class Server {
    public static void main(String[] args) throws Exception {
        try {
            // Step 1: create the server socket.
            ServerSocket serverSocket = new ServerSocket(4445);

            // Step 2: keep listening for new client connections.
            while (true) {
                // Step 3: accept one client socket.
                Socket socket = serverSocket.accept();

                // Step 4: create a thread to handle that client.
                new ClientHandler(socket).start();
            }
        } catch (Exception exception) {
            System.out.println("TCP buffered server error: " + exception.getMessage());
        }
    }
}
