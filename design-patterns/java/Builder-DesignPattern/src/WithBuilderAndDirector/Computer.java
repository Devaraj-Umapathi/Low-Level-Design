package WithBuilderAndDirector;

// Product Class
public class Computer {
    private final String HDD;
    private final String RAM;
    private final String graphicsCard;
    private final String bluetooth;

    Computer(String HDD, String RAM, String graphicsCard, String bluetooth) {
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
}

// Builder Interface
interface ComputerBuilder {
    ComputerBuilder enableGraphicsCard(String graphicsCard);
    ComputerBuilder enableBluetooth(String bluetooth);
    Computer build();
}

// Concrete Builder
class GamingComputerBuilder implements ComputerBuilder {
    // Required parameters
    private final String HDD;
    private final String RAM;

    // Optional parameters
    private String graphicsCard;
    private String bluetooth;

    public GamingComputerBuilder(String HDD, String RAM) {
        this.HDD = HDD;
        this.RAM = RAM;
    }

    @Override
    public ComputerBuilder enableGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
        return this;
    }

    @Override
    public ComputerBuilder enableBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
        return this;
    }

    @Override
    public Computer build() {
        return new Computer(HDD, RAM, graphicsCard, bluetooth);
    }
}

class OfficeComputerBuilder implements ComputerBuilder {
    // Required parameters
    private final String HDD;
    private final String RAM;

    // Optional parameters
    private String graphicsCard;
    private String bluetooth;

    public OfficeComputerBuilder(String HDD, String RAM) {
        this.HDD = HDD;
        this.RAM = RAM;
    }

    @Override
    public ComputerBuilder enableGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
        return this;
    }

    @Override
    public ComputerBuilder enableBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
        return this;
    }

    @Override
    public Computer build() {
        return new Computer(HDD, RAM, graphicsCard, bluetooth);
    }
}

// Director
class ComputerDirector {

    public Computer constructGamingComputer() {
        return new GamingComputerBuilder("1 TB", "16 GB")
                .enableGraphicsCard("ROG")
                .enableBluetooth("SONY")
                .build();
    }

    public Computer constructOfficeComputer() {
        return new OfficeComputerBuilder("500 GB", "8 GB")
                .enableBluetooth("SONY")
                .build();
    }
}


class BuilderPatternDemo {
    public static void main(String[] args) {

        ComputerDirector computerDirector = new ComputerDirector();

        // Building a Gaming Computer
        Computer gamingComputer = computerDirector.constructGamingComputer();
        System.out.println("Gaming Computer: " + gamingComputer);

        // Building an Office Computer
        Computer officeComputer = computerDirector.constructOfficeComputer();
        System.out.println("Office Computer: " + officeComputer);
    }
}
