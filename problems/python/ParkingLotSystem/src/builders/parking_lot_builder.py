"""Builder for parking lot floors and spots."""
from __future__ import annotations
from typing import Optional
from enums.vehicle_size import VehicleSize
from model.parking_floor import ParkingFloor
from service.parking_lot_system import ParkingLotSystem


class ParkingLotBuilder:
    def __init__(self):
        self._floors: list[ParkingFloor] = []
        self._current_floor: Optional[ParkingFloor] = None

    def add_floor(self, floor_number: int) -> "ParkingLotBuilder":
        self._current_floor = ParkingFloor(floor_number)
        self._floors.append(self._current_floor)
        return self

    def add_spot(self, size: VehicleSize, spot_id: str) -> "ParkingLotBuilder":
        if self._current_floor is None:
            raise RuntimeError("Call add_floor() before add_spot()")
        self._current_floor.add_spot(size, spot_id)
        return self

    def build(self) -> list[ParkingFloor]:
        return list(self._floors)

    def build_and_add_to(self, system: ParkingLotSystem) -> None:
        for floor in self.build():
            system.add_floor(floor)
