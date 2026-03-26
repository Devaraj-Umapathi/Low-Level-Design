"""Python demo equivalent of Java VendingMachineDemo."""
from enums.product_type import ProductType
from models.product import Product
from services.vending_machine import VendingMachine


def main() -> None:
    vending_machine = VendingMachine.get_instance()

    biscuit = Product(1, "Good-Day", 10.0, ProductType.BISCUITS)
    chocolate = Product(2, "Dairy Milk", 100.0, ProductType.CHOCOLATE)
    chips = Product(3, "Lays", 5.0, ProductType.CHIPS)

    vending_machine.load_product(1, biscuit, 3)
    vending_machine.load_product(2, chocolate, 15)
    vending_machine.load_product(3, chips, 5)

    vending_machine.display_products()

    print("****************************************")
    vending_machine.insert_money(20.0)
    vending_machine.select_product(3)
    print("****************************************")

    print("****************************************")
    vending_machine.insert_money(50.0)
    vending_machine.select_product(2)
    print("****************************************")

    print("****************************************")
    vending_machine.insert_money(5.0)
    vending_machine.select_product(3)
    print("****************************************")

    print("****************************************")
    vending_machine.insert_money(10.0)
    vending_machine.select_product(1)
    vending_machine.insert_money(20.0)
    vending_machine.select_product(1)
    vending_machine.insert_money(20.0)
    vending_machine.select_product(1)
    vending_machine.insert_money(10.0)
    vending_machine.select_product(1)
    print("****************************************")

    vending_machine.restock(1, 10)

    print("****************************************")
    vending_machine.insert_money(10.0)
    vending_machine.select_product(1)
    print("****************************************")


if __name__ == "__main__":
    main()
