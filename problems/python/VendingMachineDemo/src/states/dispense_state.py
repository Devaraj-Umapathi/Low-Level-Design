"""Dispense state: delivers product and returns change."""
from states.state import State
from models.rack import Rack


class DispenseState(State):
    def insert_money(self, vending_machine: "VendingMachine", amount: float) -> None:
        print("[DispenseState] Can't insert money while dispensing")

    def select_product(self, vending_machine: "VendingMachine", rack_number: int) -> None:
        print("[DispenseState] Can't select product while dispensing")

    def dispense(self, vending_machine: "VendingMachine", rack: Rack) -> None:
        product = rack.get_product()
        print(f"[DispenseState] Dispensing: {product.name}")
        rack.dispense_one()
        paid = vending_machine.get_current_amount()
        change = paid - product.price
        if change > 0:
            print(f"[DispenseState] Change: Rs {change}")
