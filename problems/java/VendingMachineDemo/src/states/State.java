package states;

import services.VendingMachine;

public interface State {
    void insertMoney(VendingMachine vendingMachine, Double amount);
    void selectProduct(VendingMachine vendingMachine, Integer rackNumber);
}
