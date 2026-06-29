# Exercise 1:

Write a Java program using UP sockets where multiple clients can send a string and a command to the server. The server
performs the requested operation on the string and returns the result.

## Server side:

1. Listens for incoming UP packets on a specified port.
2. Receives a message (**COMMAND** and **message**) where **COMMAND** can be:
    - **UPPER:** Converts the message to uppercase
    - **REVERSE:** Reverses the message
    - **LENGTH:** Returns the number of characters
3. Sends the result of the operation back to the client.

## Client Side

1. Prompts the user to input a string and choose an operation (UPPER, REVERSE, LENGTH).
2. Sends the message (with the COMMAND and the message) to the server using UDP.
3. Receives the result from the server.
4. Displays the result to the user.