package service;

import model.ParkingTicket;
import strategy.payment.PaymentStrategy;

/**
 * Delegates payment to a configured strategy. Used at exit before releasing the spot.
 */
public class PaymentService {
    private final PaymentStrategy strategy;

    public PaymentService(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Processes the payment for the given ticket and fee.
     *
     * @param ticket the parking ticket
     * @param fee    the amount to charge
     * @return true if payment succeeded, false otherwise
     */
    public boolean pay(ParkingTicket ticket, double fee) {
        boolean success = strategy.pay(fee, ticket);
        if (!success) {
            System.out.println("Payment failed for ticket: " + ticket.getTicketId());
        }
        return success;
    }
}
