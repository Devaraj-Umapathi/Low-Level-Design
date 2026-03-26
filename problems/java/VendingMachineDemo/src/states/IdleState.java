package states;

import services.VendingMachine;

public class IdleState implements State {
    @Override
    public void insertMoney(VendingMachine vendingMachine, Double amount) {
        System.out.println("[IdleState] Rs " + amount + " inserted successfully");
        vendingMachine.addAmount(amount);
        vendingMachine.setState(vendingMachine.getMoneyInsertedState());
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, Integer rackNumber) {
        System.out.println("[IdleState] Insert money before selecting a product");
    }
}
