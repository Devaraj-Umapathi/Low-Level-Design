"""
Violates ISP: Robot is forced to implement eat() even though it doesn't need it.
"""
from abc import ABC, abstractmethod


class Worker(ABC):
    @abstractmethod
    def work(self) -> None:
        pass

    @abstractmethod
    def eat(self) -> None:
        pass


class Human(Worker):
    def work(self) -> None:
        print("Human working...")

    def eat(self) -> None:
        print("Humans eating...")


class Robot(Worker):
    def work(self) -> None:
        print("Robot working...")

    def eat(self) -> None:
        raise NotImplementedError("Robots do not eat!")


def main():
    human = Human()
    human.work()
    human.eat()

    robot = Robot()
    robot.work()
    robot.eat()  # Causes error


if __name__ == "__main__":
    main()
