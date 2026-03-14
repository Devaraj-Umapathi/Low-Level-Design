package model;

import java.util.UUID;

/**
 * Links a parked vehicle to its spot and entry/exit times. Used for fee calculation and to
 * release the spot on exit (after successful payment).
 */
public class ParkingTicket {

    private final String ticketId;
    private final Vehicle parkedVehicle;
    private final ParkingSpot assignedSpot;
    private final long startTimeStamp;
    private long endTimeStamp;

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        this.parkedVehicle = vehicle;
        this.assignedSpot = spot;
        this.ticketId = UUID.randomUUID().toString();
        this.startTimeStamp = System.currentTimeMillis();
    }

    public String getTicketId() {
        return ticketId;
    }

    public ParkingSpot getAssignedSpot() {
        return assignedSpot;
    }

    /** Duration from start until end (or now if ticket not yet closed). */
    public double getDurationInHours() {
        long endTime = (endTimeStamp == 0) ? System.currentTimeMillis() : endTimeStamp;
        return (double) (endTime - startTimeStamp) / (1000 * 60 * 60);
    }

    /**
     * Sets the exit time. Called at exit before fee calculation.
     */
    public void setEndTimeStamp() {
        endTimeStamp = System.currentTimeMillis();
    }
}
