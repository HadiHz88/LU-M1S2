package java_threads.ex5;

// Ex5: compares thread priorities. Priority is only a scheduling hint.
public class Main {
    public static void main(String[] args) {
        // Step 1: create three thread objects.
        MyThread lowPriorityThread = new MyThread();
        MyThread normalPriorityThread = new MyThread();
        MyThread highPriorityThread = new MyThread();

        // Step 2: give each thread a name based on its priority.
        lowPriorityThread.setName("Low Priority");
        normalPriorityThread.setName("Normal Priority");
        highPriorityThread.setName("High Priority");

        // Step 3: assign the thread priorities.
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
        normalPriorityThread.setPriority(Thread.NORM_PRIORITY);
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);

        // Step 4: start all threads.
        lowPriorityThread.start();
        normalPriorityThread.start();
        highPriorityThread.start();
    }
}
