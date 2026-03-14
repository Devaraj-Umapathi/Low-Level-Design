package strategy.parking;

import model.ParkingFloor;
import model.ParkingSpot;
import model.Vehicle;

import java.util.List;

public class NearestFirstStrategy implements ParkingStrategy {

    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        for (ParkingFloor parkingFloor : floors) {
            for (ParkingSpot parkingSpot : parkingFloor.getSpots()) {
                if (parkingSpot.canFitVehicle(vehicle)) {
                    return parkingSpot;
                }
            }
        }
        return null;
    }
}
