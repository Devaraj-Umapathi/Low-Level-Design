import enums.PaymentMode;
import enums.VehicleSize;
import exceptions.InvalidTicketException;
import exceptions.PaymentFailedException;
import factories.VehicleFactory;
import model.ParkingTicket;
import model.Vehicle;
import service.ParkingLotSystem;

public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLotSystem parkingLotSystem = ParkingLotSystem.getInstance();

        // Admin adds floor and spots to the system
        // Get builder from system

        parkingLotSystem.createBuilder()
                .addFloor(0)
                .addSpot(VehicleSize.SMALL, "0-A")
                .addSpot(VehicleSize.MEDIUM, "0-B")
                .addSpot(VehicleSize.LARGE, "0-C")
                .addFloor(1)
                .addSpot(VehicleSize.MEDIUM, "1-A")
                .addSpot(VehicleSize.SMALL, "1-B")
                .addSpot(VehicleSize.MEDIUM, "1-C")
                .buildAndAddTo(parkingLotSystem);

        parkingLotSystem.printFloors();

        try {
            // Car (MEDIUM) enters
            Vehicle vehicle1 = VehicleFactory.createVehicle("vehicle1-xyz", VehicleSize.MEDIUM);
            ParkingTicket ticket = parkingLotSystem.parkVehicle(vehicle1);

            // Truck (LARGE) enters
            Vehicle vehicle2 = VehicleFactory.createVehicle("vehicle2-abc", VehicleSize.LARGE);
            ParkingTicket ticket2 = parkingLotSystem.parkVehicle(vehicle2);

            // Bike (SMALL) enters
            Vehicle vehicle3 = VehicleFactory.createVehicle("vehicle3-qwe", VehicleSize.SMALL);
            ParkingTicket ticket3 = parkingLotSystem.parkVehicle(vehicle3);

            parkingLotSystem.printFloors();

            // Simulate 5 seconds parked
            Thread.sleep(5000);

            parkingLotSystem.unParkVehicle(ticket.getTicketId(), PaymentMode.CASH);

            parkingLotSystem.unParkVehicle(ticket2.getTicketId(), PaymentMode.CARD);

            parkingLotSystem.unParkVehicle(ticket3.getTicketId(), PaymentMode.CASH);

            parkingLotSystem.printFloors();

//            // Truck 1 (LARGE) enters
//            Vehicle truck1 = VehicleFactory.createVehicle("Truck-abc", VehicleSize.LARGE);
//            // Truck 2 (Large) enters
//            Vehicle truck2 = VehicleFactory.createVehicle("Truck-xyz", VehicleSize.LARGE);
//            Thread t1 = new Thread(() -> parkingLotSystem.parkVehicle(truck1));
//            Thread t2 = new Thread(() -> parkingLotSystem.parkVehicle(truck2));
//            t1.start();
//            t2.start();
//            t1.join();
//            t2.join();
//            parkingLotSystem.printFloors();

        } catch (InvalidTicketException | PaymentFailedException e) {
            System.err.println("Unpark failed: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while sleeping.");
        }
    }
}