"""State interface for vending machine lifecycle."""
from __future__ import annotations
from abc import ABC, abstractmethod
from typing import TYPE_CHECKING

if TYPE_CHECKING:
    from services.vending_machine import VendingMachine


class State(ABC):
    @abstractmethod
    def insert_money(self, vending_machine: "VendingMachine", amount: float) -> None:
        pass

    @abstractmethod
    def select_product(self, vending_machine: "VendingMachine", rack_number: int) -> None:
        pass
