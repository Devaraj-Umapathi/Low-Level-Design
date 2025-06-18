//Concrete Observer
public class AmazonUser implements Customer {
    private String name;

    public AmazonUser(String name) {
        this.name = name;
    }

    @Override
    public void notify(String productName) {
        System.out.println(name + ", your requested product \"" + productName + "\" is now live on Amazon!");
    }
}
