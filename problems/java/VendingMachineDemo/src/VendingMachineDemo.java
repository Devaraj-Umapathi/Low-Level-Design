import enums.ProductType;
import models.Product;
import services.VendingMachine;

public class VendingMachineDemo {
    public static void main(String[] args) {

        VendingMachine vendingMachine = VendingMachine.getInstance();

        // Machine has fixed racks capacity; operator only loads / restocks.
        // Operator: Creating products/items
        Product biscuit = new Product(1, "Good-Day", 10.0, ProductType.BISCUITS);
        Product chocolate = new Product(2, "Dairy Milk", 100.0, ProductType.CHOCOLATE);
        Product chips = new Product(3, "Lays", 5.0, ProductType.CHIPS);

        // Operator: loading products into machine
        vendingMachine.loadProduct(1, biscuit, 3);
        vendingMachine.loadProduct(2, chocolate, 15);
        vendingMachine.loadProduct(3, chips, 5);

        vendingMachine.displayProducts();

        System.out.println("****************************************");
        // Customer: Inserts Money (Extra money than product price)
        vendingMachine.insertMoney(20.0);
        vendingMachine.selectProduct(3);
        System.out.println("****************************************");

        System.out.println("****************************************");
        // Customer: Inserts Money (Less than product price)
        vendingMachine.insertMoney(50.0);
        vendingMachine.selectProduct(2);
        System.out.println("****************************************");

        System.out.println("****************************************");
        // Customer: Exact Payment (Equal to product price)
        vendingMachine.insertMoney(5.0);
        vendingMachine.selectProduct(3);
        System.out.println("****************************************");

        System.out.println("****************************************");
        // Customer: Depletes rack & retry (Until No quantity left)
        vendingMachine.insertMoney(10.0);
        vendingMachine.selectProduct(1);
        vendingMachine.insertMoney(20.0);
        vendingMachine.selectProduct(1);
        vendingMachine.insertMoney(20.0);
        vendingMachine.selectProduct(1);
        vendingMachine.insertMoney(10.0);
        vendingMachine.selectProduct(1);
        System.out.println("****************************************");

        // Operator: Restock (Increase the quantity of the product in the rack)
        vendingMachine.reStock(1, 10);

        System.out.println("****************************************");
        // Customer: Tries after restock (After restock, the quantity of the product in the rack is increased)
        vendingMachine.insertMoney(10.0);
        vendingMachine.selectProduct(1);
        System.out.println("****************************************");

    }
}
