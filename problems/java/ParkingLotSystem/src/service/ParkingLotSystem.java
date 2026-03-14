package service;

import builders.ParkingLotBuilder;
import enums.PaymentMode;
import exceptions.InvalidTicketException;
import exceptions.PaymentFailedException;
import factories.PaymentFactory;
import model.ParkingFloor;
import model.ParkingSpot;
import model.ParkingTicket;
import model.Vehicle;
import strategy.fee.FeeStrategy;
import strategy.fee.FlatRateFeeStrategy;
import strategy.parking.NearestFirstStrategy;
import strategy.parking.ParkingStrategy;
import strategy.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central coordinator for the parking lot. Manages floors, spot assignment, tickets, and exit.
 */
public class ParkingLotSystem {

    private static volatile ParkingLotSystem instance;
    private static final Object lock = new Object();
    private final List<ParkingFloor> floors;
    private ParkingStrategy parkingStrategy;
    private FeeStrategy feeStrategy;
    private final ConcurrentHashMap<String, ParkingTicket> activeTickets;

    private ParkingLotSystem() {
        floors = new ArrayList<>();
        activeTickets = new ConcurrentHashMap<>();
        parkingStrategy = new NearestFirstStrategy();
        feeStrategy = new FlatRateFeeStrategy(5.0);
    }

    public static ParkingLotSystem getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ParkingLotSystem();
                }
            }
        }
        return instance;
    }

    public void addFloor(ParkingFloor floor) {
        if (floor == null) {
            throw new IllegalArgumentException("floor cannot be null");
        }
        floors.add(floor);
    }

    /**
     * Returns a builder to define floors and spots. Client does not need to know ParkingLotBuilder.
     * Example: getInstance().createBuilder().addFloor(0).addSpot(...).buildAndAddTo(system)
     */
    public ParkingLotBuilder createBuilder() {
        return new ParkingLotBuilder();
    }

    /**
     * Parks the vehicle and returns a ticket. Thread-safe: if two threads race for the same spot,
     * one gets it and the other retries with another spot (or returns null if none left).
     *
     * @param vehicle the vehicle to park
     * @return the parking ticket, or null if no suitable spot is available after retries
     */
    public ParkingTicket parkVehicle(Vehicle vehicle) {
        int maxAttempts = floors.stream().mapToInt(f -> f.getSpots().size()).sum();
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            ParkingSpot parkingSpot = parkingStrategy.findSpot(floors, vehicle);
            if (parkingSpot == null) {
                // No spot is free and matching size right now (parking full for this vehicle type)
                System.out.println("No parking spot available to park vehicle " + vehicle.getVehicleNumber());
                break;
            }
            ParkingTicket parkingTicket = parkingSpot.parkVehicle(vehicle);
            if (parkingTicket == null) {
                // Another thread took this spot between findSpot and parkVehicle; retry with next candidate
                System.out.println(vehicle.getVehicleNumber() + " -> Spot " + parkingSpot.getSpotId() + " taken by another vehicle, retrying...");
                continue;
            }
            activeTickets.put(parkingTicket.getTicketId(), parkingTicket);
            System.out.println("Vehicle " + vehicle.getVehicleNumber() + " parked. Ticket: " + parkingTicket.getTicketId());
            return parkingTicket;
        }
        return null;
    }

    /**
     * Unparks the vehicle for the given ticket. Payment is attempted first; the spot is
     * released and the ticket removed only when payment succeeds. If payment fails, the spot
     * remains occupied and the ticket remains in the system.
     *
     * @param ticketId    the ticket issued when the vehicle was parked
     * @param paymentMode the payment method to use (e.g. CASH, CARD)
     * @throws InvalidTicketException if the ticket ID is unknown or already used
     * @throws PaymentFailedException if payment fails (spot is not released, ticket not removed)
     */
    public void unParkVehicle(String ticketId, PaymentMode paymentMode) {
        ParkingTicket parkingTicket = activeTickets.get(ticketId);
        if (parkingTicket == null) {
            throw new InvalidTicketException(ticketId);
        }

        parkingTicket.setEndTimeStamp();

        double fee = feeStrategy.calculateFee(parkingTicket);
        System.out.println("Total fee for duration of " + parkingTicket.getDurationInHours() + " hours is : " + fee);

        PaymentStrategy paymentStrategy = PaymentFactory.get(paymentMode);
        PaymentService paymentService = new PaymentService(paymentStrategy);
        boolean paymentSuccess = paymentService.pay(parkingTicket, fee);

        if (!paymentSuccess) {
            throw new PaymentFailedException(ticketId);
        }

        parkingTicket.getAssignedSpot().unParkVehicle();
        activeTickets.remove(ticketId);

        System.out.println("Vehicle exited. Fee charged: ₹" + fee);
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void printFloors() {
        System.out.println("***********************************************************************************");
        for (ParkingFloor floor : floors) {
            System.out.println("Floor: " + floor.getFloorNumber());
            for (ParkingSpot spot : floor.getSpots()) {
                System.out.println(spot.getSpotId() + " [" + spot.getSpotSize() + "] - " + (spot.isOccupied() ? "Occupied":"Free"));
            }
        }
        System.out.println("***********************************************************************************");
    }
}
