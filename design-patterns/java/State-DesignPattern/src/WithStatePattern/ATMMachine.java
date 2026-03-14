package WithStatePattern;

public class ATMMachine {

    private ATMState currentState;
    private int balance;

    public ATMMachine(int balance) {
        this.balance = balance;
        this.currentState = new IdleState();
    }

    public void setState(ATMState state){
        this.currentState = state;
    }

    public void insertCard(){
        currentState.insertCard(this);
    }

    public void enterPin(int pin){
        currentState.enterPin(this, pin);
    }

    public void withdrawCash(int amount){
        currentState.withdrawCash(this, amount);
    }

    public void ejectCard(){
        currentState.ejectCard(this);
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }
}
