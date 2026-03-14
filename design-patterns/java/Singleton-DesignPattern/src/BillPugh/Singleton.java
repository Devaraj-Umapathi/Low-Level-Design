package BillPugh;

public class Singleton {

    // Private constructor prevents instantiation from other classes
    private Singleton() {
        System.out.println("Singleton instance created");
    }

    // Static inner class - loaded only when getInstance() is called
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }

    // Global access point
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}


class Main {
    public static void main(String[] args) {
        System.out.println("Calling getInstance first time:");
        Singleton s1 = Singleton.getInstance();

        System.out.println("Calling getInstance second time:");
        Singleton s2 = Singleton.getInstance();

        System.out.println("Are both instances same? " + (s1 == s2));
    }
}

