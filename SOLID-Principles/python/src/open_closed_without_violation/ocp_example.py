"""
OCP: New shapes can be added without modifying the existing code.
"""
import math
from abc import ABC, abstractmethod


class Shape(ABC):
    @abstractmethod
    def calculate_area(self) -> float:
        pass


class Circle(Shape):
    def __init__(self, radius: float):
        self._radius = radius

    def calculate_area(self) -> float:
        return math.pi * self._radius * self._radius


class Rectangle(Shape):
    def __init__(self, length: float, breadth: float):
        self._length = length
        self._breadth = breadth

    def calculate_area(self) -> float:
        return self._length * self._breadth


class AreaCalculator:
    def calculate_area(self, shape: Shape) -> float:
        return shape.calculate_area()


def main():
    circle = Circle(5)
    rectangle = Rectangle(4, 6)
    calculator = AreaCalculator()

    print("Circle Area:", calculator.calculate_area(circle))
    print("Rectangle Area:", calculator.calculate_area(rectangle))


if __name__ == "__main__":
    main()
