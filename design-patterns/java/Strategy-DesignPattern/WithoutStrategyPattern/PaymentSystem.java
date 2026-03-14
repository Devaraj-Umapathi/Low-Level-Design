package WithoutStrategyPattern;

// The "Bad" Way: All logic in one place
public class PaymentSystem {
    public void processPayment(String type, int amount) {
        if (type.equalsIgnoreCase("CREDIT_CARD")) {
            System.out.println("Processing Credit Card payment of $" + amount);
        } else if (type.equalsIgnoreCase("PAYPAL")) {
            System.out.println("Processing PayPal payment of $" + amount);
        } else if (type.equalsIgnoreCase("BANK_TRANSFER")) {
            System.out.println("Processing Bank Transfer payment of $" + amount);
        } else if (type.equalsIgnoreCase("Gpay")) {
            System.out.println("Processing Bank Transfer payment of $" + amount);
        } else {
            System.out.println("Payment method not supported.");
        }
    }
}

/*
  The Problem:
  If you want to add "Google Pay," you must open this file
  and add another else if. This is risky and messy.
 */