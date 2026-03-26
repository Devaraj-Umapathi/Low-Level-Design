package states;

import models.Product;
import models.Rack;
import services.VendingMachine;

public class MoneyInsertedState implements State {
    @Override
    public void insertMoney(VendingMachine vendingMachine, Double amount) {
        System.out.println("[MoneyInsertedState] Adding Rs " + amount + " (balance was Rs " + vendingMachine.getCurrentAmount() + ")");
        vendingMachine.addAmount(amount);
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, Integer rackNumber) {
        Rack rack = vendingMachine.getInventory().getRack(rackNumber);
        if (rack == null || rack.getQuantity() <= 0) {
            System.out.println("[MoneyInsertedState] Rack is empty or doesn't exist.");
            vendingMachine.refund();
            return;
        }
        Product product = rack.getProduct();
        Double productPrice = product.getPrice();
        Double paid = vendingMachine.getCurrentAmount();
        System.out.printf("[MoneyInsertedState] Price Rs %.2f, paid Rs %.2f.%n", productPrice, paid);
        if (paid < productPrice) {
            System.out.println("[MoneyInsertedState] Insufficient funds (paid Rs " + paid + ", need Rs " + productPrice + ")");
            vendingMachine.refund();
            return;
        }
        System.out.println("[MoneyInsertedState] Payment OK — dispensing");
        vendingMachine.setState(vendingMachine.getDispenseState());
        vendingMachine.beginDispensing(rack);
    }
}
