package services;

import models.Inventory;
import models.Product;
import models.Rack;
import states.DispenseState;
import states.IdleState;
import states.MoneyInsertedState;
import states.State;

public class VendingMachine {

    private static final Integer RACK_SLOT_COUNT = 3;
    private static final Integer MAX_UNITS_PER_RACK = 20;

    private static volatile VendingMachine instance;
    private static final Object lock = new Object();

    private Inventory inventory;
    private State currentState;
    private Double currentAmount;

    private IdleState idleState;
    private MoneyInsertedState moneyInsertedState;
    private DispenseState dispenseState;

    private VendingMachine () {
        currentAmount = 0.0;
        inventory = new Inventory(RACK_SLOT_COUNT, MAX_UNITS_PER_RACK);
        idleState = new IdleState();
        moneyInsertedState = new MoneyInsertedState();
        dispenseState = new DispenseState();
        currentState = idleState;
    }

    public static VendingMachine getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new VendingMachine();
                }
            }
        }
        return instance;
    }

    public void setState(State state) {
        currentState = state;
    }

    public void loadProduct(Integer rackNumber, Product product, Integer quantity) {
        if (product == null || quantity == null || quantity < 0) {
            System.out.println("Can't load product: need a product and a non-negative quantity.");
            return;
        }
        Rack rack = inventory.getRack(rackNumber);
        if (rack == null) {
            System.out.println("Can't load product. Rack no " + rackNumber + " doesn't exist.");
            return;
        }
        if (quantity > rack.getMaxCapacity()) {
            System.out.println("Can't load product: quantity " + quantity + " exceeds rack "
                    + rackNumber + " max (" + rack.getMaxCapacity() + ").");
            return;
        }
        rack.loadProduct(product, quantity);
    }

    public void reStock(Integer rackNumber, Integer quantity) {
        if (quantity == null || quantity < 0) {
            System.out.println("Can't restock: quantity must be non-null and non-negative.");
            return;
        }
        Rack rack = inventory.getRack(rackNumber);
        if (rack == null) {
            System.out.println("Can't restock. Rack no " + rackNumber + " doesn't exist.");
            return;
        }
        if (!rack.reStock(quantity)) {
            System.out.println("Can't restock rack " + rackNumber + ": would exceed max ("
                    + rack.getMaxCapacity() + ").");
            return;
        }
        System.out.println("Restock successful for rack " + rackNumber + " with quantity : " + quantity);
    }

    public void insertMoney(Double amount) {
        currentState.insertMoney(this, amount);
    }

    public void selectProduct(Integer rackNumber) {
        currentState.selectProduct(this, rackNumber);
    }

    public void addAmount(Double amount) {
        currentAmount += amount;
    }

    public void refund() {
        System.out.println("Refunding amount: Rs " + currentAmount);
        reset();
    }

    public void beginDispensing(Rack rack) {
        dispenseState.dispense(this, rack);
        reset();
    }

    public void reset() {
        setState(idleState);
        currentAmount = 0.0;
    }

    // getters
    public Inventory getInventory() {
        return inventory;
    }

    public MoneyInsertedState getMoneyInsertedState() {
        return moneyInsertedState;
    }
    public IdleState getIdleState() {
        return idleState;
    }
    public DispenseState getDispenseState() {
        return dispenseState;
    }
    public Double getCurrentAmount() {
        return currentAmount;
    }

    // Display products : Testing purpose
    public void displayProducts () {
        System.out.println("=== Inventory Status ===");
        for (int rackNo = 1; rackNo <= inventory.getRackCount(); rackNo++) {
            Rack rack = inventory.getRack(rackNo);
            Product product = rack.getProduct();
            Integer quantity = rack.getQuantity();
            System.out.printf(
                        "Rack %d: %s (id=%d, type=%s) @ Rs %.2f x%d/%d%n",
                        rackNo,
                        product.getName(),
                        product.getProductId(),
                        product.getProductType(),
                        product.getPrice(),
                        quantity,
                        rack.getMaxCapacity()
                );
        }
        System.out.println("========================");
    }
}
