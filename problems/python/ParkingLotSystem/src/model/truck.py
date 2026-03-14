"""Truck vehicle."""
from model.vehicle import Vehicle
from enums.vehicle_size import VehicleSize


class Truck(Vehicle):
    def __init__(self, vehicle_number: str, size: VehicleSize):
        super().__init__(vehicle_number, size)
