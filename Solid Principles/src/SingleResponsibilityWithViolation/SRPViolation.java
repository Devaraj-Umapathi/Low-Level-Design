package SingleResponsibilityWithViolation;


/**
 * Without SingleResponsibility Principle
 * This class violates SRP because it handles both salary calculation and report generation.
 */
// Violates SRP: This class has multiple responsibilities (salary calculation & report generation)
class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double calculateSalary() {
        return salary * 1.2; // Example salary calculation
    }

    public void generateReport() {
        System.out.println("Generating employee report...");
    }
}

public class SRPViolation {
    public static void main(String[] args) {
        Employee emp = new Employee("John", 50000);
        System.out.println("Salary: " + emp.calculateSalary());
        emp.generateReport();
    }
}




