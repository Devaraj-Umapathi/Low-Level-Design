"""Abstract vehicle."""
from abc import ABC
from enums.vehicle_size import VehicleSize


class Vehicle(ABC):
    def __init__(self, vehicle_number: str, vehicle_size: VehicleSize):
        self._vehicle_number = vehicle_number
        self._vehicle_size = vehicle_size

    def get_vehicle_number(self) -> str:
        return self._vehicle_number

    def get_vehicle_size(self) -> VehicleSize:
        return self._vehicle_size
