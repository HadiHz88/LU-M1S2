package exams.final_24_25_1.exercise_2;

import java.net.*;
import java.util.List;

public class Server {
    public record User(String userName, String password) {
    }

    public static List<User> userList = List.of(
            new User("Hadi", "Pass@1234"),
            new User("Hanine", "Pass@1234"),
            new User("Messi", "Pass@1234"
            ));


    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(9876);

        while (true) {
            Socket client = ss.accept();

            System.out.println("Accepting " + client.getInetAddress().getAddress().toString());

            ServerHandler handler = new ServerHandler(client);

            handler.start();
        }
    }

}
