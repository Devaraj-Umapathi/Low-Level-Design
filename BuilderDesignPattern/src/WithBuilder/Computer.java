package WithBuilder;

public class Computer {
    private final String HDD;
    private final String RAM;
    private final String graphicsCard;
    private final String bluetooth;

    private Computer(String HDD, String RAM, String graphicsCard, String bluetooth) {
        this.HDD = HDD;
        this.RAM = RAM;
        this.graphicsCard = graphicsCard;
        this.bluetooth = bluetooth;
    }

    @Override
    public String toString() {
        return "Computer [CPU=" + HDD + ", RAM=" + RAM +
                ", GraphicsCardEnabled=" + graphicsCard +
                ", BluetoothEnabled=" + bluetooth + "]";
    }

    // Static nested Builder class
    public static class ComputerBuilder {
        // Required parameters
        private final String HDD;
        private final String RAM;

        // Optional parameters
        private String graphicsCard;
        private String bluetooth;

        // Constructor for required parameters
        public ComputerBuilder(String HDD, String RAM) {
            this.HDD = HDD;
            this.RAM = RAM;
        }

        // Setter for graphics card
        public ComputerBuilder enableGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        // Setter for Bluetooth
        public ComputerBuilder enableBluetooth(String bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        // Build method to create Computer object
        public Computer build() {
            return new Computer(HDD, RAM, graphicsCard, bluetooth);
        }
    }
}

class TestBuilderPattern {
    public static void main(String[] args) {
        // Using the builder to create a Computer object
        Computer gamingComputer = new Computer.ComputerBuilder("1 TB", "16 GB")
                .enableGraphicsCard("ROG")
                .enableBluetooth("SONY")
                .build();

        // Display the Computer object's details
        System.out.println(gamingComputer);

        Computer basicComputer = new Computer.ComputerBuilder("500 GB", "8 GB")
                .enableBluetooth("SONY")
                .build();

        System.out.println(basicComputer);
    }
}