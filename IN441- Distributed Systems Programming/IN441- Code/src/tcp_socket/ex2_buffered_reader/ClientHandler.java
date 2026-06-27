package tcp_socket.ex2_buffered_reader;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Step 4: create a reader to receive the client values.
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Step 5: create a writer to send the result.
            PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);

            // Step 6: read the two numbers and the operator.
            double firstNumber = Double.parseDouble(fromClient.readLine());
            double secondNumber = Double.parseDouble(fromClient.readLine());
            String operator = fromClient.readLine();

            // Step 7: apply the required operation.
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

            // Step 8: send the result back to the client.
            toClient.println(result);
        } catch (Exception exception) {
            System.out.println("Error while handling BufferedReader client: " + exception.getMessage());
        } finally {
            try {
                // Step 9: close the client socket.
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
