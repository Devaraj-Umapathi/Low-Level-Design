package EagerInitialization;

/**
 * ✅ Thread-safe by default.
 * ❌ Wastes memory if the instance is never used.
 */
class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() { }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println("Requesting instance the first time:");
        Singleton obj1 = Singleton.getInstance();

        System.out.println("Requesting instance the second time:");
        Singleton obj2 = Singleton.getInstance();

        // Verifying if both instances are the same
        System.out.println("Are both instances same? " + (obj1 == obj2));
    }
}
