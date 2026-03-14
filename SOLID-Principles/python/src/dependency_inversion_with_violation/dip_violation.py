"""
Violates DIP: Computer is tightly coupled to WiredKeyboard.
"""


class WiredKeyboard:
    def connect(self) -> None:
        print("Wired keyboard connected")


class Computer:
    def __init__(self) -> None:
        self._keyboard = WiredKeyboard()  # Tight coupling

    def start(self) -> None:
        self._keyboard.connect()


def main():
    pc = Computer()
    pc.start()


if __name__ == "__main__":
    main()
