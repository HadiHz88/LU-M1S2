package udp_socket.ex2;

import java.net.*;

public class ClientHandler extends Thread {
    private final DatagramSocket serverSocket;
    private final DatagramPacket packet;

    public ClientHandler(DatagramPacket packet, DatagramSocket server) {
        this.packet = packet;
        this.serverSocket = server;
    }

    @Override
    public void run() {
        try {
            // Step 5: convert the received packet into text.
            String message = new String(packet.getData(), 0, packet.getLength());
            String[] parts = message.split(",");

            // Step 6: extract the two numbers and the operator.
            double firstNumber = Double.parseDouble(parts[0]);
            double secondNumber = Double.parseDouble(parts[1]);
            String operator = parts[2];

            // Step 7: apply the requested arithmetic operation.
            double result;
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        throw new IllegalArgumentException("Division by zero is not allowed.");
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }

            // Step 8: convert the result into bytes.
            byte[] sendBuffer = String.valueOf(result).getBytes();

            // Step 9: create the response packet with the original client address and port.
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, packet.getAddress(), packet.getPort());

            // Step 10: send the response packet, not the original request packet.
            serverSocket.send(sendPacket);
        } catch (Exception exception) {
            System.out.println("UDP client handler error: " + exception.getMessage());
        }
    }
}
