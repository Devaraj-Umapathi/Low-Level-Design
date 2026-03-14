"""Authenticated state - PIN correct, can withdraw."""
from .atm_state import ATMState
from .atm_machine import ATMMachine


class AuthenticatedState(ATMState):
    def insert_card(self, atm: ATMMachine) -> None:
        print("Card already inserted")

    def enter_pin(self, atm: ATMMachine, pin: int) -> None:
        print("Already authenticated")

    def withdraw_cash(self, atm: ATMMachine, amount: int) -> None:
        from .cash_dispensed_state import CashDispensedState
        if atm.get_balance() >= amount:
            print(f"Dispensing cash: {amount}")
            atm.set_balance(atm.get_balance() - amount)
            atm.set_state(CashDispensedState())
        else:
            print("Insufficient balance")

    def eject_card(self, atm: ATMMachine) -> None:
        from .idle_state import IdleState
        print("Card ejected")
        atm.set_state(IdleState())
