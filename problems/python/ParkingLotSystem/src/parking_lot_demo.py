"""Parking lot demo - Python version."""
import time
from enums.payment_mode import PaymentMode
from enums.vehicle_size import VehicleSize
from factories.vehicle_factory import VehicleFactory
from model.parking_ticket import ParkingTicket
from model.vehicle import Vehicle
from service.parking_lot_system import ParkingLotSystem


def main() -> None:
    parking_lot_system = ParkingLotSystem()

    parking_lot_system.create_builder() \
        .add_floor(0) \
        .add_spot(VehicleSize.SMALL, "0-A") \
        .add_spot(VehicleSize.MEDIUM, "0-B") \
        .add_spot(VehicleSize.LARGE, "0-C") \
        .add_floor(1) \
        .add_spot(VehicleSize.MEDIUM, "1-A") \
        .add_spot(VehicleSize.SMALL, "1-B") \
        .add_spot(VehicleSize.MEDIUM, "1-C") \
        .build_and_add_to(parking_lot_system)

    parking_lot_system.print_floors()

    try:
        vehicle1 = VehicleFactory.create_vehicle("vehicle1-xyz", VehicleSize.MEDIUM)
        ticket = parking_lot_system.park_vehicle(vehicle1)

        vehicle2 = VehicleFactory.create_vehicle("vehicle2-abc", VehicleSize.LARGE)
        ticket2 = parking_lot_system.park_vehicle(vehicle2)

        vehicle3 = VehicleFactory.create_vehicle("vehicle3-qwe", VehicleSize.SMALL)
        ticket3 = parking_lot_system.park_vehicle(vehicle3)

        parking_lot_system.print_floors()

        time.sleep(2)  # Simulate 2 seconds parked

        if ticket:
            parking_lot_system.un_park_vehicle(ticket.get_ticket_id(), PaymentMode.CASH)
        if ticket2:
            parking_lot_system.un_park_vehicle(ticket2.get_ticket_id(), PaymentMode.CARD)
        if ticket3:
            parking_lot_system.un_park_vehicle(ticket3.get_ticket_id(), PaymentMode.CASH)

        parking_lot_system.print_floors()
    except ValueError as e:
        print("Unpark failed:", e)


if __name__ == "__main__":
    main()
