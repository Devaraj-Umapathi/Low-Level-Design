"""Ticket linking vehicle to spot and timestamps."""
from __future__ import annotations
import uuid
import time
from model.vehicle import Vehicle


class ParkingTicket:
    def __init__(self, vehicle: Vehicle, spot: "ParkingSpot"):
        self._parked_vehicle = vehicle
        self._assigned_spot = spot
        self._ticket_id = str(uuid.uuid4())
        self._start_time_stamp = time.time() * 1000
        self._end_time_stamp: float = 0

    def get_ticket_id(self) -> str:
        return self._ticket_id

    def get_assigned_spot(self) -> "ParkingSpot":
        return self._assigned_spot

    def get_duration_in_hours(self) -> float:
        end_time = self._end_time_stamp if self._end_time_stamp else time.time() * 1000
        return (end_time - self._start_time_stamp) / (1000 * 60 * 60)

    def set_end_time_stamp(self) -> None:
        self._end_time_stamp = time.time() * 1000
