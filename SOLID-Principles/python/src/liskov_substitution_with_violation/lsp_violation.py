"""
Violates LSP: Penguin cannot fly, but it inherits a fly() method.
"""


class Bird:
    def fly(self) -> None:
        print("Flying...")


class Sparrow(Bird):
    def fly(self) -> None:
        print("Sparrow can fly")


class Penguin(Bird):
    def fly(self) -> None:
        raise NotImplementedError("Penguins can't fly!")


def main():
    sparrow: Bird = Sparrow()
    penguin: Bird = Penguin()
    sparrow.fly()
    penguin.fly()  # This will break at runtime


if __name__ == "__main__":
    main()
