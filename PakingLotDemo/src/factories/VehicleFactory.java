package factories;

import enums.VehicleSize;
import model.Bike;
import model.Car;
import model.Truck;
import model.Vehicle;

/**
 * Factory for creating vehicles by size (SMALL→Bike, MEDIUM→Car, LARGE→Truck).
 */
public class VehicleFactory {

    /**
     * Creates a vehicle of the appropriate type for the given size.
     *
     * @param vehicleNumber the vehicle identifier (e.g. license plate)
     * @param vehicleSize   the size (SMALL, MEDIUM, LARGE)
     * @return the created vehicle
     * @throws IllegalArgumentException if vehicleSize is unknown
     */
    public static Vehicle createVehicle(String vehicleNumber, VehicleSize vehicleSize) {
        return switch (vehicleSize) {
            case LARGE -> new Truck(vehicleNumber, vehicleSize);
            case MEDIUM -> new Car(vehicleNumber, vehicleSize);
            case SMALL -> new Bike(vehicleNumber, vehicleSize);
            default -> throw new IllegalArgumentException("Unknown Vehicle: " + vehicleSize);
        };
    }
}
