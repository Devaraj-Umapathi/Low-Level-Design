package WithStatePattern;

public class IdleState implements ATMState {

    public void insertCard(ATMMachine atm) {
        System.out.println("Card inserted");
        atm.setState(new HasCardState());
    }

    public void enterPin(ATMMachine atm, int pin) {
        System.out.println("Insert card first");
    }

    public void withdrawCash(ATMMachine atm, int amount) {
        System.out.println("Insert card first");
    }

    public void ejectCard(ATMMachine atm) {
        System.out.println("No card to eject");
    }
}
