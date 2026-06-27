package java_threads.ex6;

// Ex6: synchronized prevents lost updates when many threads increment the same counter.
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        // Step 4: synchronized allows only one thread at a time to update the counter.
        count++;
    }

    public int getCount() {
        return count;
    }
}
