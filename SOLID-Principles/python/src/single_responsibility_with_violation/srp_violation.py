"""
Without Single Responsibility Principle.
This class violates SRP: salary calculation and report generation in one class.
"""


class Employee:
    def __init__(self, name: str, salary: float):
        self.name = name
        self.salary = salary

    def calculate_salary(self) -> float:
        return self.salary * 1.2

    def generate_report(self) -> None:
        print("Generating employee report...")


def main():
    emp = Employee("John", 50000)
    print("Salary:", emp.calculate_salary())
    emp.generate_report()


if __name__ == "__main__":
    main()
