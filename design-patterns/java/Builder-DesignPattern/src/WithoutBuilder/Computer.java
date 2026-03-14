package WithoutBuilder;

public class Computer {
    // Required parameters
    private final String HDD;
    private final String RAM;

    // Optional parameters
    private final String graphicsCard;
    private final String bluetooth;

    // Constructor with all parameters
    public Computer(String HDD, String RAM, String graphicsCard, String bluetooth) {
        this.HDD = HDD;
        this.RAM = RAM;
        this.graphicsCard = graphicsCard;
        this.bluetooth = bluetooth;
    }

    public Computer(String HDD, String RAM ) {
        this.HDD = HDD;
        this.RAM = RAM;
        this.graphicsCard = null;
        this.bluetooth = null;
    }


    @Override
    public String toString() {
        return "Computer [HDD=" + HDD +
                ", RAM=" + RAM +
                ", GraphicsCardEnabled=" + graphicsCard +
                ", BluetoothEnabled=" + bluetooth + "]";
    }
}

class Client {
    public static void main(String[] args) {

        // Creating a computer with all parameters
        Computer computer = new Computer("1 TB", "16 GB", "ROG", "SONY");

        // Display the computer configurations
        System.out.println(computer);

        // When customer request to create computer without graphicsCard and bluetooth
        Computer computer2 = new Computer("1 TB", "16 GB", null, null);

        Computer computer3 = new Computer("1 TB", "16 GB");
        System.out.println(computer2);
        System.out.println(computer3);

    }
}



/**
 * ⚠️ Disadvantages of Not Using the Builder Pattern
 *
 * Telescoping Constructors:
 * As the number of parameters increases, especially optional ones, the number of constructor combinations grows exponentially. This leads to multiple constructors with varying parameter lists, making the code harder to maintain and understand.​
 *
 * Reduced Readability:
 * When multiple parameters of the same type are used, it's easy to confuse their order, leading to potential bugs. For example, distinguishing between two String parameters like HDD and RAM can be error-prone.​
 *
 * Limited Flexibility:
 * Adding new optional parameters requires modifying existing constructors or adding new ones, which can break existing code and reduce flexibility.​
 *
 * Immutability Challenges:
 * Without the Builder pattern, achieving immutability becomes more complex, especially when dealing with optional parameters.​
 * LinkedIn
 *
 * Maintenance Overhead:
 * Managing multiple constructors and ensuring consistency across them increases the maintenance burden, particularly in large codebases.
 */