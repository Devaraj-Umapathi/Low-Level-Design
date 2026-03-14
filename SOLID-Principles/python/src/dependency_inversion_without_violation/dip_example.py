"""
DIP: Depend on abstraction (Keyboard); Computer is not tied to concrete implementation.
"""
from abc import ABC, abstractmethod


class Keyboard(ABC):
    @abstractmethod
    def connect(self) -> None:
        pass


class WiredKeyboard(Keyboard):
    def connect(self) -> None:
        print("Wired keyboard connected")


class WirelessKeyboard(Keyboard):
    def connect(self) -> None:
        print("Wireless keyboard connected")


class Computer:
    def __init__(self, keyboard: Keyboard) -> None:
        self._keyboard = keyboard

    def start(self) -> None:
        self._keyboard.connect()


def main():
    wired = WiredKeyboard()
    pc1 = Computer(wired)
    pc1.start()

    wireless = WirelessKeyboard()
    pc2 = Computer(wireless)
    pc2.start()


if __name__ == "__main__":
    main()
