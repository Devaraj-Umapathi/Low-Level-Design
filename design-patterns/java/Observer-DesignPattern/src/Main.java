public class Main {
    public static void main(String[] args) {
        ProductLaunchNotifier productLaunchNotifier = new iPhoneLaunchNotifier("iphone 18");

        Customer user1 = new AmazonUser("Mike");
        Customer user2 = new AmazonUser("Math");
        Customer user3 = new AmazonUser("Rocky");

        productLaunchNotifier.subscribe(user1);
        productLaunchNotifier.subscribe(user2);
        productLaunchNotifier.subscribe(user3);

        productLaunchNotifier.unsubscribe(user2);

        productLaunchNotifier.notifyCustomers();
    }
}