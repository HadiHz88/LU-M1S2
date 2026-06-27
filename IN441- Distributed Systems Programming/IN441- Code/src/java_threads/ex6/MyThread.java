package java_threads.ex6;

public class MyThread extends Thread {
    private final Counter counter;

    public MyThread(Counter counter) {
        // Step 2: store the shared counter so both threads work on the same object.
        this.counter = counter;
    }

    @Override
    public void run() {
        // Step 3: each thread increments the shared counter many times.
        for (int i = 0; i < 10000; i++) {
            counter.increment();
        }
    }
}
