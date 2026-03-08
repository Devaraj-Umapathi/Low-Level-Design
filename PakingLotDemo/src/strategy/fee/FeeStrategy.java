package strategy.fee;

import model.ParkingTicket;

/**
 * Strategy for calculating the parking fee based on ticket (e.g. duration).
 */
public interface FeeStrategy {

    /**
     * Calculates the fee for the given ticket.
     *
     * @param parkingTicket the ticket (includes duration via getDurationInHours())
     * @return the fee amount
     */
    double calculateFee(ParkingTicket parkingTicket);
}
