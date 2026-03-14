"""Nearest-first parking strategy."""
from __future__ import annotations
from typing import Optional
from model.parking_floor import ParkingFloor
from model.parking_spot import ParkingSpot
from model.vehicle import Vehicle
from strategy.parking.parking_strategy import ParkingStrategy


class NearestFirstStrategy(ParkingStrategy):
    def find_spot(
        self, floors: list[ParkingFloor], vehicle: Vehicle
    ) -> Optional[ParkingSpot]:
        for floor in floors:
            for spot in floor.get_spots():
                if spot.can_fit_vehicle(vehicle):
                    return spot
        return None
