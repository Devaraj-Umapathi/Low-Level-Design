package WithStrategyPattern;

public class PaymentSystem {
    private PaymentStrategy strategy;

    // This allows the strategy to be swapped at runtime
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(int amount) {
        strategy.pay(amount);
    }
}
