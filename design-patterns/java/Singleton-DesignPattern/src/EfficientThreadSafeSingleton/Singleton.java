package EfficientThreadSafeSingleton;

public class Singleton {
    private static volatile Singleton instance;  // Volatile keyword ensures visibility

    private Singleton() { }

    public static Singleton getInstance() {
        Singleton localInstance = instance;
        if (localInstance == null) {  // First check (No Lock)
            synchronized (Singleton.class) {  // Thread safety
                if (localInstance == null) {  // Second check (Inside Lock)
                    localInstance = new Singleton();
                }
                instance = localInstance;
            }
        }
        return localInstance;
    }
}

class Main {
    public static void main(String[] args) {
        Runnable task = () -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " → " + instance.hashCode());
        };

        // Start two threads at the same time
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }
}

/**
 * Why is this better?
 * ✅ Only synchronizes the first time (reduces performance overhead).
 * ✅ Ensures instance is created safely in a multi-threaded environment.
 */