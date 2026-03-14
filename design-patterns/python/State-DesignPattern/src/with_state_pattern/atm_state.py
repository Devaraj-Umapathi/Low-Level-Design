"""State interface - all concrete states implement this."""
from abc import ABC, abstractmethod
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from .atm_machine import ATMMachine


class ATMState(ABC):
    @abstractmethod
    def insert_card(self, atm: "ATMMachine") -> None:
        pass

    @abstractmethod
    def enter_pin(self, atm: "ATMMachine", pin: int) -> None:
        pass

    @abstractmethod
    def withdraw_cash(self, atm: "ATMMachine", amount: int) -> None:
        pass

    @abstractmethod
    def eject_card(self, atm: "ATMMachine") -> None:
        pass
