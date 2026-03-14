"""
Violates OCP: Every time a new shape is added, we need to modify the existing class.
"""
import math


class AreaCalculator:
    def calculate_area(
        self, shape: str, radius: float = 0, length: float = 0, breadth: float = 0
    ) -> float:
        if shape == "circle":
            return math.pi * radius * radius
        elif shape == "rectangle":
            return length * breadth
        return 0.0


def main():
    calculator = AreaCalculator()
    print("Circle Area:", calculator.calculate_area("circle", 5, 0, 0))


if __name__ == "__main__":
    main()
