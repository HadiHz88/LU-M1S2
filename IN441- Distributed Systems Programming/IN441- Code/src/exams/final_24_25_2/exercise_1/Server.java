package exams.final_24_25_2.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final Map<String, Player> players = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        // Step 1: create the UDP game server socket.
        DatagramSocket server = new DatagramSocket(6790);
        System.out.println("UDP game server is running on port 6790...");

        while (true) {
            // Step 2: receive one packet and let a helper thread process it.
            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            server.receive(receivePacket);
            new GameServerHelper(server, receivePacket, players).start();
        }
    }
}
