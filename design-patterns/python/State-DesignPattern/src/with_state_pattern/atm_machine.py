"""Context - ATM machine that delegates to current state."""
from .atm_state import ATMState
from .idle_state import IdleState


class ATMMachine:
    def __init__(self, balance: int) -> None:
        self._balance = balance
        self._current_state: ATMState = IdleState()

    def set_state(self, state: ATMState) -> None:
        self._current_state = state

    def insert_card(self) -> None:
        self._current_state.insert_card(self)

    def enter_pin(self, pin: int) -> None:
        self._current_state.enter_pin(self, pin)

    def withdraw_cash(self, amount: int) -> None:
        self._current_state.withdraw_cash(self, amount)

    def eject_card(self) -> None:
        self._current_state.eject_card(self)

    def get_balance(self) -> int:
        return self._balance

    def set_balance(self, balance: int) -> None:
        self._balance = balance
