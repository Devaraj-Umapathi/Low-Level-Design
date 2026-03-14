package model;

import enums.VehicleSize;

import java.util.ArrayList;
import java.util.List;

/**
 * A floor in the parking lot, containing an ordered list of parking spots.
 */
public class ParkingFloor {

    private final int floorNumber;
    private final List<ParkingSpot> spots;

    /**
     * @param floorNumber the floor index (e.g. 0 for ground)
     */
    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public void addSpot(VehicleSize size, String id) {
        spots.add(new ParkingSpot(size, id));
    }
}
