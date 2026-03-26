package states;

import models.Product;
import models.Rack;
import services.VendingMachine;

public class DispenseState implements State {

    @Override
    public void insertMoney(VendingMachine vendingMachine, Double amount) {
        System.out.println("[DispenseState] Can't insert money while dispensing");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine, Integer rackNumber) {
        System.out.println("[DispenseState] Can't select product while dispensing");
    }

    public void dispense(VendingMachine vendingMachine, Rack rack) {
        Product product = rack.getProduct();
        System.out.println("[DispenseState] Dispensing: " + product.getName());
        rack.dispenseOne();
        double paid = vendingMachine.getCurrentAmount();
        double change = paid - product.getPrice();
        if (change > 0) {
            System.out.println("[DispenseState] Change: Rs " + change);
        }
    }
}
