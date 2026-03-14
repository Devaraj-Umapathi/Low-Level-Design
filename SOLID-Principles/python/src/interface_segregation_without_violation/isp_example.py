"""
ISP: Separate interfaces so Robot doesn't implement eat().
"""
from abc import ABC, abstractmethod


class Workable(ABC):
    @abstractmethod
    def work(self) -> None:
        pass


class Eatable(ABC):
    @abstractmethod
    def eat(self) -> None:
        pass


class Human(Workable, Eatable):
    def work(self) -> None:
        print("Developer is coding...")

    def eat(self) -> None:
        print("Developer is eating...")


class Robot(Workable):
    def work(self) -> None:
        print("Robot is working...")


def main():
    human = Human()
    human.work()
    human.eat()

    robot = Robot()
    robot.work()


if __name__ == "__main__":
    main()
