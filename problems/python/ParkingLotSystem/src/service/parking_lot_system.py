"""Central coordinator for the parking lot (Singleton)."""
from __future__ import annotations
import threading
from typing import Optional
from model.parking_floor import ParkingFloor
from model.parking_spot import ParkingSpot
from model.parking_ticket import ParkingTicket
from model.vehicle import Vehicle
from enums.payment_mode import PaymentMode
from strategy.parking.parking_strategy import ParkingStrategy
from strategy.parking.nearest_first_strategy import NearestFirstStrategy
from strategy.fee.fee_strategy import FeeStrategy
from strategy.fee.flat_rate_fee_strategy import FlatRateFeeStrategy


class ParkingLotSystem:
    _instance: Optional["ParkingLotSystem"] = None
    _lock = threading.Lock()

    def __new__(cls) -> "ParkingLotSystem":
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self) -> None:
        if hasattr(self, "_initialized") and self._initialized:
            return
        self._floors: list[ParkingFloor] = []
        self._active_tickets: dict[str, ParkingTicket] = {}
        self._parking_strategy: ParkingStrategy = NearestFirstStrategy()
        self._fee_strategy: FeeStrategy = FlatRateFeeStrategy(5.0)
        self._initialized = True

    def add_floor(self, floor: ParkingFloor) -> None:
        if floor is None:
            raise ValueError("floor cannot be null")
        self._floors.append(floor)

    def create_builder(self) -> "ParkingLotBuilder":
        from builders.parking_lot_builder import ParkingLotBuilder
        return ParkingLotBuilder()

    def park_vehicle(self, vehicle: Vehicle) -> Optional[ParkingTicket]:
        max_attempts = sum(len(f.get_spots()) for f in self._floors)
        for _ in range(max_attempts):
            spot = self._parking_strategy.find_spot(self._floors, vehicle)
            if spot is None:
                print(f"No parking spot available to park vehicle {vehicle.get_vehicle_number()}")
                break
            ticket = spot.park_vehicle(vehicle)
            if ticket is None:
                print(f"{vehicle.get_vehicle_number()} -> Spot {spot.get_spot_id()} taken, retrying...")
                continue
            self._active_tickets[ticket.get_ticket_id()] = ticket
            print(f"Vehicle {vehicle.get_vehicle_number()} parked. Ticket: {ticket.get_ticket_id()}")
            return ticket
        return None

    def un_park_vehicle(self, ticket_id: str, payment_mode: PaymentMode) -> None:
        ticket = self._active_tickets.get(ticket_id)
        if ticket is None:
            raise ValueError(f"Invalid ticket: {ticket_id}")
        ticket.set_end_time_stamp()
        fee = self._fee_strategy.calculate_fee(ticket)
        print(f"Total fee for duration of {ticket.get_duration_in_hours():.2f} hours is: {fee}")
        # Simplified: assume payment always succeeds
        ticket.get_assigned_spot().un_park_vehicle()
        del self._active_tickets[ticket_id]
        print(f"Vehicle exited. Fee charged: ₹{fee}")

    def print_floors(self) -> None:
        print("*" * 80)
        for floor in self._floors:
            print(f"Floor: {floor.get_floor_number()}")
            for spot in floor.get_spots():
                status = "Occupied" if spot.is_occupied() else "Free"
                print(f"  {spot.get_spot_id()} [{spot.get_spot_size().value}] - {status}")
        print("*" * 80)
