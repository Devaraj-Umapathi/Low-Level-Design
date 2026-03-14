"""A single parking space."""
from __future__ import annotations
import threading
from typing import Optional
from enums.vehicle_size import VehicleSize
from model.vehicle import Vehicle
from model.parking_ticket import ParkingTicket


class ParkingSpot:
    def __init__(self, size: VehicleSize, spot_id: str):
        self._spot_id = spot_id
        self._spot_size = size
        self._is_occupied = False
        self._lock = threading.Lock()
        self._parked_vehicle: Optional[Vehicle] = None

    def is_occupied(self) -> bool:
        return self._is_occupied

    def get_spot_size(self) -> VehicleSize:
        return self._spot_size

    def get_spot_id(self) -> str:
        return self._spot_id

    def can_fit_vehicle(self, vehicle: Vehicle) -> bool:
        return not self._is_occupied and self._spot_size == vehicle.get_vehicle_size()

    def park_vehicle(self, vehicle: Vehicle) -> Optional[ParkingTicket]:
        with self._lock:
            if self._is_occupied or self._spot_size != vehicle.get_vehicle_size():
                return None
            self._is_occupied = True
            self._parked_vehicle = vehicle
            return ParkingTicket(vehicle, self)

    def un_park_vehicle(self) -> None:
        self._is_occupied = False
        self._parked_vehicle = None
