package SingletonUsingEnum;

/**
 * ✅ Prevents multiple instances (Serialization & Reflection Safe)
 * ✅ Simpler than synchronized or volatile-based approaches
 */
enum Singleton {
    INSTANCE;

    public void showMessage() {
        System.out.println("Singleton using Enum!");
    }
}

public class EnumSingletonDemo {
    public static void main(String[] args) {
        Singleton.INSTANCE.showMessage();
    }
}