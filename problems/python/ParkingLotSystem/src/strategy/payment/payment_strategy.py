"""Strategy for processing payment when a vehicle exits."""
from __future__ import annotations
from abc import ABC, abstractmethod
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from model.parking_ticket import ParkingTicket


class PaymentStrategy(ABC):
    @abstractmethod
    def pay(self, fee: float, ticket: "ParkingTicket") -> bool:
        """Process payment. Returns True if succeeded, False otherwise."""
        pass
