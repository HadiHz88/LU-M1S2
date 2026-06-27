package java_threads.ex4;

// Ex4: demonstrates thread naming and Thread.yield().
public class Main {
    public static void main(String[] args) {
        // Step 1: create the first thread object.
        MyThread firstThread = new MyThread();

        // Step 2: create the second thread object.
        MyThread secondThread = new MyThread();

        // Step 3: give each thread a readable name.
        firstThread.setName("Thread-1");
        secondThread.setName("Thread-2");

        // Step 4: start both threads.
        firstThread.start();
        secondThread.start();
    }
}
