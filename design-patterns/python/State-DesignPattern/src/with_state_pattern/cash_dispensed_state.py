"""CashDispensed state - cash given, waiting for card to be taken."""
from .atm_state import ATMState
from .atm_machine import ATMMachine


class CashDispensedState(ATMState):
    def insert_card(self, atm: ATMMachine) -> None:
        print("Transaction in progress")

    def enter_pin(self, atm: ATMMachine, pin: int) -> None:
        print("Transaction in progress")

    def withdraw_cash(self, atm: ATMMachine, amount: int) -> None:
        print("Transaction completed")

    def eject_card(self, atm: ATMMachine) -> None:
        from .idle_state import IdleState
        print("Please take your card")
        atm.set_state(IdleState())
