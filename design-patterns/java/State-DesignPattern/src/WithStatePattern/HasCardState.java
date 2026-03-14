package WithStatePattern;

public class HasCardState implements ATMState {

    public void insertCard(ATMMachine atm) {
        System.out.println("Card already inserted");
    }

    public void enterPin(ATMMachine atm, int pin) {

        if(pin == 1234){
            System.out.println("PIN correct");
            atm.setState(new AuthenticatedState());
        } else {
            System.out.println("Wrong PIN");
            atm.setState(new IdleState());
        }
    }

    public void withdrawCash(ATMMachine atm, int amount) {
        System.out.println("Enter PIN first");
    }

    public void ejectCard(ATMMachine atm) {
        System.out.println("Card ejected");
        atm.setState(new IdleState());
    }
}
