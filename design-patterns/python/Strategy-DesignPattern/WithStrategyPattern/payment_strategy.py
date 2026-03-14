"""Payment strategy interface."""
from abc import ABC, abstractmethod


class PaymentStrategy(ABC):
    @abstractmethod
    def pay(self, amount: int) -> None:
        pass
