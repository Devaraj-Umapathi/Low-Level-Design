package WithoutStrategyPattern;

public class PaymentApp {
    // Main Class
    public static void main(String[] args) {
        PaymentSystem system = new PaymentSystem();

        // If user selects CREDIT_CARD
        system.processPayment("CREDIT_CARD", 100);

        // If user selects PAYPAL
        system.processPayment("PAYPAL", 50);
    }
}
