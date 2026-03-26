"""Idle state: accepts initial money, blocks product selection."""
from states.state import State


class IdleState(State):
    def insert_money(self, vending_machine: "VendingMachine", amount: float) -> None:
        print(f"[IdleState] Rs {amount} inserted successfully")
        vending_machine.add_amount(amount)
        vending_machine.set_state(vending_machine.get_money_inserted_state())

    def select_product(self, vending_machine: "VendingMachine", rack_number: int) -> None:
        print("[IdleState] Insert money before selecting a product")
