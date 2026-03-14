package builders;

import enums.VehicleSize;
import model.ParkingFloor;
import service.ParkingLotSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating a parking lot structure (floors and spots) without
 * exposing ParkingFloor / ParkingSpot construction to the client.
 * Client uses this API instead of instantiating model classes directly.
 */
public class ParkingLotBuilder {

    private final List<ParkingFloor> floors = new ArrayList<>();
    private ParkingFloor currentFloor;

    /**
     * Adds a new floor with the given number. Subsequent addSpot() calls
     * will add spots to this floor until the next addFloor().
     */
    public ParkingLotBuilder addFloor(int floorNumber) {
        currentFloor = new ParkingFloor(floorNumber);
        floors.add(currentFloor);
        return this;
    }

    /**
     * Adds a spot to the current floor. Must call addFloor() first.
     */
    public ParkingLotBuilder addSpot(VehicleSize size, String spotId) {
        if (currentFloor == null) {
            throw new IllegalStateException("Call addFloor() before addSpot()");
        }
        currentFloor.addSpot(size, spotId);
        return this;
    }

    /**
     * Returns the built list of floors. Client can pass these to ParkingLotSystem.addFloor().
     */
    public List<ParkingFloor> build() {
        return new ArrayList<>(floors);
    }

    /**
     * Builds and adds all floors to the system. Client does not need to reference ParkingFloor or List.
     */
    public void buildAndAddTo(ParkingLotSystem system) {
        build().forEach(system::addFloor);
    }
}
