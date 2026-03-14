package WithStatePattern;

public class Main {

    public static void main(String[] args) {

        ATMMachine atm = new ATMMachine(10000);

        atm.insertCard();

        atm.enterPin(1234);

        atm.withdrawCash(2000);

        atm.ejectCard();
    }
}
