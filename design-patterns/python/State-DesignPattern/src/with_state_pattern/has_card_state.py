"""HasCard state - card inserted, PIN not yet entered."""
from .atm_state import ATMState
from .atm_machine import ATMMachine


class HasCardState(ATMState):
    def insert_card(self, atm: ATMMachine) -> None:
        print("Card already inserted")

    def enter_pin(self, atm: ATMMachine, pin: int) -> None:
        from .idle_state import IdleState
        from .authenticated_state import AuthenticatedState
        if pin == 1234:
            print("PIN correct")
            atm.set_state(AuthenticatedState())
        else:
            print("Wrong PIN")
            atm.set_state(IdleState())

    def withdraw_cash(self, atm: ATMMachine, amount: int) -> None:
        print("Enter PIN first")

    def eject_card(self, atm: ATMMachine) -> None:
        from .idle_state import IdleState
        print("Card ejected")
        atm.set_state(IdleState())
