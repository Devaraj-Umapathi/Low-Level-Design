//Concrete Subject
import java.util.ArrayList;
import java.util.List;

public class iPhoneLaunchNotifier implements ProductLaunchNotifier {
    private List<Customer> subscribers = new ArrayList<>();
    private String productName;

    public iPhoneLaunchNotifier(String productName) {
        this.productName = productName;
    }

    @Override
    public void subscribe(Customer customer) {
        subscribers.add(customer);
    }

    @Override
    public void unsubscribe(Customer customer) {
        subscribers.remove(customer);
    }

    @Override
    public void notifyCustomers() {
        System.out.println("\n🔔 Amazon: " + productName + " is now available for purchase!");
        for (Customer customer : subscribers) {
            customer.notify(productName);
        }
    }
}
