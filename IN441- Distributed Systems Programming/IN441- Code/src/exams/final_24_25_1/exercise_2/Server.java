package exams.final_24_25_1.exercise_2;

import java.net.*;
import java.util.HashMap;

public class Server {
    public static final HashMap<String, String> users = new HashMap<>();

    static {
        users.put("Hadi", "Pass@1234");
        users.put("Messi", "Pass@1234");
    }


    public static void main(String[] args) throws Exception {
        // Step 1: create the TCP server socket.
        ServerSocket ss = new ServerSocket(9876);

        while (true) {
            // Step 2: accept a client connection.
            Socket client = ss.accept();

            System.out.println("Accepting " + client.getInetAddress());

            // Step 3: handle each client in its own thread.
            ServerHandler handler = new ServerHandler(client);
            handler.start();
        }
    }

}
