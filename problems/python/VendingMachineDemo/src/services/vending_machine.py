"""Singleton coordinator for vending machine operations."""
from __future__ import annotations
import threading
from models.inventory import Inventory
from models.product import Product
from models.rack import Rack
from states.state import State
from states.idle_state import IdleState
from states.money_inserted_state import MoneyInsertedState
from states.dispense_state import DispenseState


class VendingMachine:
    RACK_SLOT_COUNT = 3
    MAX_UNITS_PER_RACK = 20

    _instance: "VendingMachine | None" = None
    _lock = threading.Lock()

    def __new__(cls) -> "VendingMachine":
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self) -> None:
        if hasattr(self, "_initialized") and self._initialized:
            return
        self._current_amount = 0.0
        self._inventory = Inventory(self.RACK_SLOT_COUNT, self.MAX_UNITS_PER_RACK)
        self._idle_state = IdleState()
        self._money_inserted_state = MoneyInsertedState()
        self._dispense_state = DispenseState()
        self._current_state: State = self._idle_state
        self._initialized = True

    @classmethod
    def get_instance(cls) -> "VendingMachine":
        return cls()

    def set_state(self, state: State) -> None:
        self._current_state = state

    def load_product(self, rack_number: int, product: Product, quantity: int) -> None:
        if product is None or quantity is None or quantity < 0:
            print("Can't load product: need a product and a non-negative quantity.")
            return
        rack = self._inventory.get_rack(rack_number)
        if rack is None:
            print(f"Can't load product. Rack no {rack_number} doesn't exist.")
            return
        if quantity > rack.get_max_capacity():
            print(
                f"Can't load product: quantity {quantity} exceeds rack "
                f"{rack_number} max ({rack.get_max_capacity()})."
            )
            return
        rack.load_product(product, quantity)

    def restock(self, rack_number: int, quantity: int) -> None:
        if quantity is None or quantity < 0:
            print("Can't restock: quantity must be non-null and non-negative.")
            return
        rack = self._inventory.get_rack(rack_number)
        if rack is None:
            print(f"Can't restock. Rack no {rack_number} doesn't exist.")
            return
        if not rack.restock(quantity):
            print(
                f"Can't restock rack {rack_number}: would exceed max "
                f"({rack.get_max_capacity()})."
            )
            return
        print(f"Restock successful for rack {rack_number} with quantity : {quantity}")

    def insert_money(self, amount: float) -> None:
        self._current_state.insert_money(self, amount)

    def select_product(self, rack_number: int) -> None:
        self._current_state.select_product(self, rack_number)

    def add_amount(self, amount: float) -> None:
        self._current_amount += amount

    def refund(self) -> None:
        print(f"Refunding amount: Rs {self._current_amount}")
        self.reset()

    def begin_dispensing(self, rack: Rack) -> None:
        self._dispense_state.dispense(self, rack)
        self.reset()

    def reset(self) -> None:
        self.set_state(self._idle_state)
        self._current_amount = 0.0

    def get_inventory(self) -> Inventory:
        return self._inventory

    def get_money_inserted_state(self) -> MoneyInsertedState:
        return self._money_inserted_state

    def get_idle_state(self) -> IdleState:
        return self._idle_state

    def get_dispense_state(self) -> DispenseState:
        return self._dispense_state

    def get_current_amount(self) -> float:
        return self._current_amount

    def display_products(self) -> None:
        print("=== Inventory Status ===")
        for rack_no in range(1, self._inventory.get_rack_count() + 1):
            rack = self._inventory.get_rack(rack_no)
            product = rack.get_product()
            quantity = rack.get_quantity()
            print(
                f"Rack {rack_no}: {product.name} "
                f"(id={product.product_id}, type={product.product_type.name}) "
                f"@ Rs {product.price:.2f} x{quantity}/{rack.get_max_capacity()}"
            )
        print("========================")
