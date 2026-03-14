package WithStatePattern;

public class AuthenticatedState implements ATMState {

    public void insertCard(ATMMachine atm) {
        System.out.println("Card already inserted");
    }

    public void enterPin(ATMMachine atm, int pin) {
        System.out.println("Already authenticated");
    }

    public void withdrawCash(ATMMachine atm, int amount) {

        if(atm.getBalance() >= amount){
            System.out.println("Dispensing cash: " + amount);
            atm.setBalance(atm.getBalance() - amount);
            atm.setState(new CashDispensedState());
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public void ejectCard(ATMMachine atm) {
        System.out.println("Card ejected");
        atm.setState(new IdleState());
    }
}
