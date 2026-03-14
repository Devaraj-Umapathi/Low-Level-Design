"""Factory for creating vehicles by size."""
from enums.vehicle_size import VehicleSize
from model.vehicle import Vehicle
from model.bike import Bike
from model.car import Car
from model.truck import Truck


class VehicleFactory:
    @staticmethod
    def create_vehicle(vehicle_number: str, vehicle_size: VehicleSize) -> Vehicle:
        if vehicle_size == VehicleSize.SMALL:
            return Bike(vehicle_number, vehicle_size)
        if vehicle_size == VehicleSize.MEDIUM:
            return Car(vehicle_number, vehicle_size)
        if vehicle_size == VehicleSize.LARGE:
            return Truck(vehicle_number, vehicle_size)
        raise ValueError(f"Unknown Vehicle: {vehicle_size}")
