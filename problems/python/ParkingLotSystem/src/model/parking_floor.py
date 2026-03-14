"""A floor in the parking lot."""
from enums.vehicle_size import VehicleSize
from model.parking_spot import ParkingSpot


class ParkingFloor:
    def __init__(self, floor_number: int):
        self._floor_number = floor_number
        self._spots: list[ParkingSpot] = []

    def get_floor_number(self) -> int:
        return self._floor_number

    def get_spots(self) -> list[ParkingSpot]:
        return self._spots

    def add_spot(self, size: VehicleSize, spot_id: str) -> None:
        self._spots.append(ParkingSpot(size, spot_id))
