package WithStrategyPattern;

public class BankTransferPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Bank Transfer.");
    }
}
