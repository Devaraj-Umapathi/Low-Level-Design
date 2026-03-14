package WithoutStatePattern;

public class ATMMachine {

    private String state; //Idle, Has card, Authenticated, Dispense
    private int balance;

    public ATMMachine(int balance) {
        this.balance = balance;
        this.state = "IDLE";
    }

    public void insertCard() {

        if (state.equals("IDLE")) {
            System.out.println("Card inserted");
            state = "HAS_CARD";
        }
        else {
            System.out.println("Card already inserted");
        }
    }

    public void enterPin(int pin) {

        if (state.equals("HAS_CARD")) {

            if (pin == 1234) {
                System.out.println("PIN correct");
                state = "AUTHENTICATED";
            } else {
                System.out.println("Wrong PIN");
                state = "IDLE";
            }

        } else if (state.equals("IDLE")) {
            System.out.println("Insert card first");
        }
        else {
            System.out.println("Already authenticated");
        }
    }

    public void withdrawCash(int amount) {

        if (state.equals("AUTHENTICATED")) {

            if (balance >= amount) {
                System.out.println("Dispensing cash: " + amount);
                balance -= amount;
                state = "IDLE";
            } else {
                System.out.println("Insufficient balance");
            }

        } else if (state.equals("HAS_CARD")) {
            System.out.println("Enter PIN first");
        }
        else {
            System.out.println("Insert card first");
        }
    }

    public void ejectCard() {

        if (state.equals("HAS_CARD") || state.equals("AUTHENTICATED")) {
            System.out.println("Card ejected");
            state = "IDLE";
        }
        else {
            System.out.println("No card inserted");
        }
    }
}
