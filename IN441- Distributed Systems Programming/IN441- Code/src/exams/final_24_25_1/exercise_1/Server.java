package exams.final_24_25_1.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

    public static void main(String[] args) throws Exception {
        DatagramSocket server = new DatagramSocket(6789);

        while (true) {
            byte[] receiveData = new byte[2048];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            server.receive(receivePacket);

            ServerHelper handler = new ServerHelper(server, receivePacket);

            handler.start();
        }
    }
}
