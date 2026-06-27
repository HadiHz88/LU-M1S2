package java_threads.ex2;

public class ThreadA extends Thread {
    @Override
    public void run() {
        // Step 4: this thread keeps printing the letter A.
        for (int i = 0; i < 100; i++) {
            System.out.println("A");
        }
    }
}
