package WithStrategyPattern;

public class PaymentApp {
    public static void main(String[] args) {
        PaymentSystem system = new PaymentSystem();

        // Scenario A: User selects Credit Card
        system.setPaymentStrategy(new CreditCardPayment());
        system.executePayment(100);

        // Scenario B: User switches to PayPal
        system.setPaymentStrategy(new PayPalPayment());
        system.executePayment(50);

        // Scenario C: User switches to BankTransfer
        system.setPaymentStrategy(new BankTransferPayment());
        system.executePayment(200);

        // Scenario D: Adding a new method (e.g., GPAY)
        // would NOT require changing PaymentSystem at all!
    }
}
