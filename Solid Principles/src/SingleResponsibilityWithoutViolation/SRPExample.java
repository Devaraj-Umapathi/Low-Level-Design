package SingleResponsibilityWithoutViolation;

// Now, each class has a single responsibility

// Employee details class
class Employee {

    private String name;

    private double salary;

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
}

// Responsible only for salary calculation
class SalaryCalculator {
    public double calculateSalary(Employee employee) {
        return employee.getSalary() * 1.2;
    }
}

// Responsible only for report generation
class ReportGenerator {
    public void generateReport(Employee employee) {
        System.out.println("Generating employee report for " + employee.getName() + "...");
    }
}

public class SRPExample {
    public static void main(String[] args) {
        Employee emp = new Employee("John", 10000);
        SalaryCalculator calculator = new SalaryCalculator();
        ReportGenerator reportGenerator = new ReportGenerator();

        reportGenerator.generateReport(emp);
        System.out.println("Salary: " + calculator.calculateSalary(emp));

    }
}

