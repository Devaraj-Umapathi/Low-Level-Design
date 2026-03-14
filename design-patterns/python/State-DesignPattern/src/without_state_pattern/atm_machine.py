"""ATM without State pattern - logic in if/else based on state string."""
from typing import Literal


class ATMMachine:
    def __init__(self, balance: int) -> None:
        self._balance = balance
        self._state: Literal["IDLE", "HAS_CARD", "AUTHENTICATED"] = "IDLE"

    def insert_card(self) -> None:
        if self._state == "IDLE":
            print("Card inserted")
            self._state = "HAS_CARD"
        else:
            print("Card already inserted")

    def enter_pin(self, pin: int) -> None:
        if self._state == "HAS_CARD":
            if pin == 1234:
                print("PIN correct")
                self._state = "AUTHENTICATED"
            else:
                print("Wrong PIN")
                self._state = "IDLE"
        elif self._state == "IDLE":
            print("Insert card first")
        else:
            print("Already authenticated")

    def withdraw_cash(self, amount: int) -> None:
        if self._state == "AUTHENTICATED":
            if self._balance >= amount:
                print(f"Dispensing cash: {amount}")
                self._balance -= amount
                self._state = "IDLE"
            else:
                print("Insufficient balance")
        elif self._state == "HAS_CARD":
            print("Enter PIN first")
        else:
            print("Insert card first")

    def eject_card(self) -> None:
        if self._state in ("HAS_CARD", "AUTHENTICATED"):
            print("Card ejected")
            self._state = "IDLE"
        else:
            print("No card inserted")
