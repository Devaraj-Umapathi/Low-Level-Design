public class ForeignCar {
    private String carName;
    private String carBrand;
    private int brandPrice;

    public ForeignCar(String carName, String carBrand, int brandPrice) {
        this.carName = carName;
        this.carBrand = carBrand;
        this.brandPrice = brandPrice;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public int getBrandPrice() {
        return brandPrice;
    }
}
