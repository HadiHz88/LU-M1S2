package exams.final_24_25_1.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerHelper extends Thread {
    private final DatagramSocket socket;
    private final DatagramPacket packet;

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
            // Step 4: convert the UDP packet to a string request.
            String message = new String(packet.getData(), 0, packet.getLength());

            // Step 5: perform the requested operation.
            String response = processRequest(message);
            byte[] responseBytes = response.getBytes();

            // Step 6: send the response to the same client address and port.
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


    static String processRequest(String message) {
        String[] parts = formatMessage(message);
        if (parts.length != 2) {
            return "Invalid input format";
        }

        return commandOperation(parts[0], parts[1]);
    }

    private static String[] formatMessage(String message) {
        return message.split(":", 2);
    }

    private static String commandOperation(String command, String msg) {
        return switch (command.trim().toUpperCase()) {
            case "UPPER" -> msg.toUpperCase();
            case "REVERSE" -> new StringBuilder(msg).reverse().toString();
            case "LENGTH" -> String.valueOf(msg.length());
            default -> "Invalid Command";
        };
    }
}
