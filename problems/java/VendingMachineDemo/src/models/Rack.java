package models;

public class Rack {

    private final Integer rackNumber;
    private final Integer maxCapacity;
    private Product product;
    private Integer quantity;

    public Rack(Integer rackNumber, Integer maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be positive");
        }
        this.rackNumber = rackNumber;
        this.maxCapacity = maxCapacity;
        quantity = 0;
    }

    public void loadProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public boolean reStock(Integer restockQuantity) {
        if (restockQuantity + quantity > maxCapacity) {
            return false;
        }
        this.quantity = restockQuantity + quantity;
        return true;
    }

    public void dispenseOne() {
        if (quantity != null && quantity > 0) {
            quantity--;
        }
    }

    //getters
    public Integer getRackNumber() {
        return rackNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }
}
