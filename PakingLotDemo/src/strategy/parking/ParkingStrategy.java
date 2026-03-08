package strategy.parking;

import model.ParkingFloor;
import model.ParkingSpot;
import model.Vehicle;

import java.util.List;

/**
 * Strategy for selecting a parking spot for a vehicle (e.g. nearest first, best fit).
 */
public interface ParkingStrategy {

    /**
     * Finds an available spot that fits the vehicle, or null if none.
     *
     * @param floors  the list of floors to search
     * @param vehicle the vehicle to park
     * @return an available spot that fits, or null if parking is full
     */
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
