package exceptions;

public class PaymentFailedException extends RuntimeException {

    public PaymentFailedException(String ticketId) {
        super("Payment failed for ticket: " + ticketId + ". Vehicle cannot exit.");
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
