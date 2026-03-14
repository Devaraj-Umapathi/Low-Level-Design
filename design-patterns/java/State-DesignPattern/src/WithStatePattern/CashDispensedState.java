package WithStatePattern;

public class CashDispensedState implements ATMState {

    public void insertCard(ATMMachine atm) {
        System.out.println("Transaction in progress");
    }

    public void enterPin(ATMMachine atm, int pin) {
        System.out.println("Transaction in progress");
    }

    public void withdrawCash(ATMMachine atm, int amount) {
        System.out.println("Transaction completed");
    }

    public void ejectCard(ATMMachine atm) {
        System.out.println("Please take your card");
        atm.setState(new IdleState());
    }
}
