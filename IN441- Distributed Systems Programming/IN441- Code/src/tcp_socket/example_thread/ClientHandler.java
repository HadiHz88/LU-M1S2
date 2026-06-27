package tcp_socket.example_thread;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Step 4: create a reader to receive the client message.
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 5: create an output stream to send the reply.
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());

            // Step 6: read the message from the client.
            String message = fromClient.readLine();

            // Step 7: send the same message back.
            toClient.writeBytes(message + "\n");
        } catch (Exception exception) {
            System.out.println("Threaded TCP handler error: " + exception.getMessage());
        } finally {
            try {
                // Step 8: close the client socket.
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
