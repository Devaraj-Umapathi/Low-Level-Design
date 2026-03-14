"""State pattern demo - ATM with state objects."""
from .atm_machine import ATMMachine


def main() -> None:
    atm = ATMMachine(10000)

    atm.insert_card()
    atm.enter_pin(1234)
    atm.withdraw_cash(2000)
    atm.eject_card()


if __name__ == "__main__":
    main()
