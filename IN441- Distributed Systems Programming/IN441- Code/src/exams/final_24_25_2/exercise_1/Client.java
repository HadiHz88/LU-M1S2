package exams.final_24_25_2.exercise_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws Exception {
        // Step 1: create the UDP client socket.
        DatagramSocket clientSocket = new DatagramSocket();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        InetAddress serverAddress = InetAddress.getByName("localhost");

        // Step 2: join the game.
        System.out.print("Enter your username: ");
        String username = input.readLine();
        sendMessage(clientSocket, serverAddress, "JOIN:" + username);
        System.out.println(receiveMessage(clientSocket));

        // Step 3: send game commands until EXIT.
        while (true) {
            System.out.println("Enter command (ANSWER:<text>, SCORE, NEXT, EXIT):");
            String command = input.readLine();

            sendMessage(clientSocket, serverAddress, command);
            String response = receiveMessage(clientSocket);
            System.out.println(response);

            if (command.equalsIgnoreCase("EXIT")) {
                break;
            }
        }

        clientSocket.close();
    }

    private static void sendMessage(
            DatagramSocket clientSocket,
            InetAddress serverAddress,
            String message
    ) throws Exception {
        byte[] toServer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(
                toServer,
                toServer.length,
                serverAddress,
                6790);

        clientSocket.send(packet);
    }

    private static String receiveMessage(DatagramSocket clientSocket) throws Exception {
        byte[] fromServer = new byte[2048];
        DatagramPacket response = new DatagramPacket(fromServer, fromServer.length);

        clientSocket.receive(response);
        return new String(response.getData(), 0, response.getLength());
    }
}
