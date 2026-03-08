package model;

import enums.VehicleSize;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A single parking space with a fixed size. Can be occupied by at most one vehicle
 * of matching size. Thread-safe: {@link #parkVehicle(Vehicle)} uses synchronization
 * to prevent two vehicles from being assigned to the same spot.
 */
public class ParkingSpot {

    private final String spotId;
    private final VehicleSize spotSize;
    private final AtomicBoolean isOccupied;
    private Vehicle parkedVehicle;

    public boolean isOccupied() {
        return isOccupied.get();
    }

    public VehicleSize getSpotSize() {
        return spotSize;
    }

    public String getSpotId() {
        return spotId;
    }

    public ParkingSpot(VehicleSize size, String id) {
        this.spotSize = size;
        this.spotId = id;
        this.isOccupied = new AtomicBoolean(false);
    }

    /**
     * Assigns the vehicle to this spot and returns a ticket. Thread-safe (synchronized).
     * Typically called after a strategy returns this spot from findSpot (which uses
     * {@link #canFitVehicle(Vehicle)}). Returns null if the spot was taken by another
     * thread or the vehicle size does not match this spot.
     *
     * @param vehicle the vehicle to park
     * @return the parking ticket linking this spot and vehicle, or null if spot is occupied or size does not fit
     */
    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
        if (isOccupied.get() || spotSize != vehicle.getVehicleSize())
            return null;
        isOccupied.set(true);
        parkedVehicle = vehicle;
        return new ParkingTicket(vehicle, this);
    }

    /**
     * Releases this spot so it can be used again. Called only after successful payment at exit.
     */
    public void unParkVehicle() {
        isOccupied.set(false);
        parkedVehicle = null;
    }

    /**
     * Returns true if this spot fits the vehicle and is free. Used by strategies to find candidates.
     * Does not claim the spot; {@link #parkVehicle(Vehicle)} does that under lock and may return null if taken.
     *
     * @param vehicle the vehicle to check
     * @return true if the spot is free and size matches, false otherwise
     */
    public boolean canFitVehicle(Vehicle vehicle) {
        return !isOccupied.get() && spotSize == vehicle.getVehicleSize();
    }
}
