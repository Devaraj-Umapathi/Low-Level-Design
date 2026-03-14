package strategy.fee;

import model.ParkingTicket;

public class FlatRateFeeStrategy implements FeeStrategy {

    private final double flatRatePerHour;

    public FlatRateFeeStrategy(double flatRatePerHour) {
        if (flatRatePerHour <= 0) {
            throw new IllegalArgumentException("flatRatePerHour must be positive");
        }
        this.flatRatePerHour = flatRatePerHour;
    }

    @Override
    public double calculateFee(ParkingTicket parkingTicket) {
        double totalHours = Math.ceil(parkingTicket.getDurationInHours());
        return totalHours * flatRatePerHour;
    }
}
