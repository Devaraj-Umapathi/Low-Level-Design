package strategy.payment;

import model.ParkingTicket;

/**
 * Strategy for processing payment when a vehicle exits the parking lot.
 */
public interface PaymentStrategy {

    /**
     * Attempts to process the payment for the given fee and ticket.
     *
     * @param fee   the amount to charge
     * @param ticket the parking ticket (for reference/logging)
     * @return true if payment succeeded, false if payment failed
     */
    boolean pay(double fee, ParkingTicket ticket);
}
