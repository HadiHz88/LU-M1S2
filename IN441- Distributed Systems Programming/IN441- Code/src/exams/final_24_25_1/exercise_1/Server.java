package exams.final_24_25_1.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public static void main(String[] args) throws Exception {
        // Step 1: create the UDP server socket.
        DatagramSocket server = new DatagramSocket(6789);

        while (true) {
            // Step 2: receive one client packet.
            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            server.receive(receivePacket);

            // Step 3: handle the packet in a thread so other clients can send requests.
            ServerHelper handler = new ServerHelper(server, receivePacket);
            handler.start();
        }
    }
}
