# Exercise 1

Write a Java program using **UDP sockets** to implement a "Multi-Client Game Server" that performs simple text
operations and score tracking. The server accepts multiple clients, processes their commands, and maintains individual
scores based on correct responses.

## Server Side

1. Listens on a specified UDP port for messages from multiple clients.
2. When a new client connects (detected by a `"JOIN:username"` message), the server:
    - Assigns a unique player ID based on IP and port.
    - Initializes their score to 0.
    - Sends a welcome message and a random challenge (e.g., "Convert hello to uppercase").
3. Processes commands from clients:
    - **`ANSWER:<text>`**: Validates the client's answer.
        - If correct, increases their score and sends `"Correct! Your score is X"`.
        - If incorrect, sends `"Wrong answer, try again."`.
    - **`SCORE`**: Sends the current score to the client.
    - **`NEXT`**: Sends a new random challenge (e.g., "Reverse the string world").
    - **`EXIT`**: Removes the client from the active players list and sends a goodbye message.

## Client Side

1. Prompts the user for a username and sends `"JOIN: username"` to the server.
2. Receives a welcome message and a challenge (e.g., "Convert hello to uppercase").
3. The user can:
    - Send **`ANSWER:<text>`** to respond.
    - Send **`SCORE`** to check their score.
    - Send **`NEXT`** to get a new challenge.
    - Send **`EXIT`** to leave the game.
