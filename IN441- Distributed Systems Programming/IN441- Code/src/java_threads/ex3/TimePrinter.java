package java_threads.ex3;

import java.time.LocalTime;

// Ex3: the main thread sleeps for one second between prints.
public class TimePrinter {
    public static void main(String[] args) {
        // Step 1: repeat the same action several times.
        for (int i = 0; i < 5; i++) {
            // Step 2: print the current time.
            System.out.println("Current Time: " + LocalTime.now());
            try {
                // Step 3: pause the thread for 1 second.
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                // Step 4: restore the interrupted status if sleep is interrupted.
                Thread.currentThread().interrupt();
                System.out.println("Sleep was interrupted.");
            }
        }
    }
}
