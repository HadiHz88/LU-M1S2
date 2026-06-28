package exams.final_24_25_1.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerHelper extends Thread {
    private DatagramSocket socket;
    private DatagramPacket packet;

    ServerHelper(
            DatagramSocket socket,
            DatagramPacket packet
    ) {
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            String message = new String(packet.getData(), 0, packet.getLength());

            String[] parts = formatMessage(message);
            String response = commandOperation(parts[0], parts[1]);

            byte[] responseBytes = response.getBytes();

            DatagramPacket reply =
                    new DatagramPacket(
                            responseBytes,
                            responseBytes.length,
                            packet.getAddress(),
                            packet.getPort());

            socket.send(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String[] formatMessage(String message) {
        return message.split(":");
    }

    private String commandOperation(String msg, String command) {
        return switch (command) {
            case "UPPER" -> msg.toUpperCase();
            case "REVERSE" -> new StringBuilder(msg).reverse().toString();
            case "LENGTH" -> String.valueOf(msg.length());
            default -> "Invalid Command";
        };
    }
}

