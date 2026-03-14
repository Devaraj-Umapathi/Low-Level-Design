"""Strategy for selecting a parking spot."""
from __future__ import annotations
from abc import ABC, abstractmethod
from typing import Optional
from model.parking_floor import ParkingFloor
from model.parking_spot import ParkingSpot
from model.vehicle import Vehicle


class ParkingStrategy(ABC):
    @abstractmethod
    def find_spot(
        self, floors: list[ParkingFloor], vehicle: Vehicle
    ) -> Optional[ParkingSpot]:
        pass
