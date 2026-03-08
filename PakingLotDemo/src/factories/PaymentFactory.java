package factories;

import enums.PaymentMode;
import strategy.payment.CardPaymentStrategy;
import strategy.payment.CashPaymentStrategy;
import strategy.payment.PaymentStrategy;

/**
 * Factory for obtaining the payment strategy for a given payment mode.
 */
public class PaymentFactory {

    /**
     * Returns the payment strategy for the given mode.
     *
     * @param mode the payment mode (e.g. CASH, CARD)
     * @return the strategy implementation
     * @throws IllegalArgumentException if mode is null or unknown
     */
    public static PaymentStrategy get(PaymentMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("PaymentMode cannot be null");
        }
        return switch (mode) {
            case CASH -> new CashPaymentStrategy();
            case CARD -> new CardPaymentStrategy();
            default -> throw new IllegalArgumentException("Unknown PaymentMode: " + mode);
        };
    }
}
