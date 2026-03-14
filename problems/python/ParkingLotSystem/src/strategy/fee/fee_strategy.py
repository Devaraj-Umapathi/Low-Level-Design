"""Fee calculation strategy."""
from abc import ABC, abstractmethod
from model.parking_ticket import ParkingTicket


class FeeStrategy(ABC):
    @abstractmethod
    def calculate_fee(self, ticket: ParkingTicket) -> float:
        pass
