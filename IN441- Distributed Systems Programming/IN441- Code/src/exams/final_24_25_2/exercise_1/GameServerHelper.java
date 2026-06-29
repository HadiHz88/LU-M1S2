package exams.final_24_25_2.exercise_1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameServerHelper extends Thread {
    private static final Random random = new Random();
    private static final List<Challenge> challenges = List.of(
            new Challenge("Convert hello to uppercase", "HELLO"),
            new Challenge("Reverse the string world", "dlrow"),
            new Challenge("Find the length of distributed", "11"),
            new Challenge("Convert socket to uppercase", "SOCKET"),
            new Challenge("Reverse the string network", "krowten")
    );

    private final DatagramSocket socket;
    private final DatagramPacket packet;
    private final Map<String, Player> players;

    GameServerHelper(DatagramSocket socket, DatagramPacket packet, Map<String, Player> players) {
        this.socket = socket;
        this.packet = packet;
        this.players = players;
    }

    @Override
    public void run() {
        try {
            // Step 3: convert the UDP packet to a client command.
            String clientId = packet.getAddress().getHostAddress() + ":" + packet.getPort();
            String message = new String(packet.getData(), 0, packet.getLength());

            // Step 4: process the command and prepare a response.
            String response = processRequest(players, clientId, message);
            byte[] responseBytes = response.getBytes();

            // Step 5: send the response to the same client address and port.
            DatagramPacket reply = new DatagramPacket(
                    responseBytes,
                    responseBytes.length,
                    packet.getAddress(),
                    packet.getPort());

            socket.send(reply);
        } catch (Exception e) {
            System.out.println("Error while handling UDP game client: " + e.getMessage());
        }
    }

    static String processRequest(Map<String, Player> players, String clientId, String message) {
        String trimmedMessage = message.trim();

        if (trimmedMessage.toUpperCase().startsWith("JOIN:")) {
            String username = trimmedMessage.substring(trimmedMessage.indexOf(":") + 1).trim();
            if (username.isEmpty()) {
                return "ERROR: Username is required";
            }

            Challenge challenge = randomChallenge();
            players.put(clientId, new Player(username, challenge.question(), challenge.answer()));
            return "Welcome " + username + "! Your player ID is " + clientId + "\n" +
                    "Challenge: " + challenge.question();
        }

        Player player = players.get(clientId);
        if (player == null) {
            return "ERROR: Please JOIN before sending commands";
        }

        if (trimmedMessage.toUpperCase().startsWith("ANSWER:")) {
            String answer = trimmedMessage.substring(trimmedMessage.indexOf(":") + 1).trim();
            if (answer.equalsIgnoreCase(player.challengeAnswer())) {
                player.increaseScore();
                Challenge challenge = randomChallenge();
                player.setChallenge(challenge.question(), challenge.answer());
                return "Correct! Your score is " + player.score() + "\n" +
                        "Challenge: " + player.challengeQuestion();
            }

            return "Wrong answer, try again.";
        }

        if (trimmedMessage.equalsIgnoreCase("SCORE")) {
            return "Your score is " + player.score();
        }

        if (trimmedMessage.equalsIgnoreCase("NEXT")) {
            Challenge challenge = randomChallenge();
            player.setChallenge(challenge.question(), challenge.answer());
            return "Challenge: " + player.challengeQuestion();
        }

        if (trimmedMessage.equalsIgnoreCase("EXIT")) {
            players.remove(clientId);
            return "Goodbye " + player.username() + "!";
        }

        return "ERROR: Invalid command";
    }

    private static Challenge randomChallenge() {
        return challenges.get(random.nextInt(challenges.size()));
    }

    private record Challenge(String question, String answer) {
    }
}
