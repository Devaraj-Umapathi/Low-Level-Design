package WithStatePattern;

public interface ATMState {

    void insertCard(ATMMachine atm);

    void enterPin(ATMMachine atm, int pin);

    void withdrawCash(ATMMachine atm, int amount);

    void ejectCard(ATMMachine atm);
}
