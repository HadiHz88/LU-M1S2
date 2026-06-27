package java_threads.ex2;

public class ThreadB extends Thread {
    @Override
    public void run() {
        // Step 5: this thread keeps printing the letter B.
        for (int i = 0; i < 100; i++) {
            System.out.println("B");
        }
    }
}
