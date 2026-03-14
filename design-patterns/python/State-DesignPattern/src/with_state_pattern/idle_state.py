"""Idle state - no card inserted."""
from __future__ import annotations
from .atm_state import ATMState


class IdleState(ATMState):
    def insert_card(self, atm: "ATMMachine") -> None:
        from .has_card_state import HasCardState
        print("Card inserted")
        atm.set_state(HasCardState())

    def enter_pin(self, atm: "ATMMachine", pin: int) -> None:
        print("Insert card first")

    def withdraw_cash(self, atm: "ATMMachine", amount: int) -> None:
        print("Insert card first")

    def eject_card(self, atm: "ATMMachine") -> None:
        print("No card to eject")
