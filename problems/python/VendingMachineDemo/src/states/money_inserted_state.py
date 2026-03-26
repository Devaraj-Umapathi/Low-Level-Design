"""Money inserted state: validates rack and payment."""
from states.state import State


class MoneyInsertedState(State):
    def insert_money(self, vending_machine: "VendingMachine", amount: float) -> None:
        print(
            f"[MoneyInsertedState] Adding Rs {amount} "
            f"(balance was Rs {vending_machine.get_current_amount()})"
        )
        vending_machine.add_amount(amount)

    def select_product(self, vending_machine: "VendingMachine", rack_number: int) -> None:
        rack = vending_machine.get_inventory().get_rack(rack_number)
        if rack is None or rack.get_quantity() <= 0:
            print("[MoneyInsertedState] Rack is empty or doesn't exist.")
            vending_machine.refund()
            return

        product = rack.get_product()
        product_price = product.price
        paid = vending_machine.get_current_amount()
        print(f"[MoneyInsertedState] Price Rs {product_price:.2f}, paid Rs {paid:.2f}.")

        if paid < product_price:
            print(
                f"[MoneyInsertedState] Insufficient funds "
                f"(paid Rs {paid}, need Rs {product_price})"
            )
            vending_machine.refund()
            return

        print("[MoneyInsertedState] Payment OK - dispensing")
        vending_machine.set_state(vending_machine.get_dispense_state())
        vending_machine.begin_dispensing(rack)
