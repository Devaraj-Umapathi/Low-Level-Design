import java.util.ArrayList;
import java.util.List;

public class CarDealerClient {
    public List<Car> getCarList() {
        List<Car> carList = new ArrayList<>();

        Car indianCar1 = new IndianCar("Punch", "Tata", 1000000);

        Car foreignCar1 = new ForeignCarAdapter(new ForeignCar("Spectre", "Rolls", 10000000));

        carList.add(indianCar1);
        carList.add(foreignCar1);

        return carList;
    }
}
