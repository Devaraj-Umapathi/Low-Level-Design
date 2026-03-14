"""
With Single Responsibility - each class has one job.
"""


class Employee:
    def __init__(self, name: str, salary: float):
        self._name = name
        self._salary = salary

    def get_name(self) -> str:
        return self._name

    def get_salary(self) -> float:
        return self._salary


class SalaryCalculator:
    def calculate_salary(self, employee: Employee) -> float:
        return employee.get_salary() * 1.2


class ReportGenerator:
    def generate_report(self, employee: Employee) -> None:
        print(f"Generating employee report for {employee.get_name()}...")


def main():
    emp = Employee("John", 10000)
    calculator = SalaryCalculator()
    report_generator = ReportGenerator()

    report_generator.generate_report(emp)
    print("Salary:", calculator.calculate_salary(emp))


if __name__ == "__main__":
    main()
