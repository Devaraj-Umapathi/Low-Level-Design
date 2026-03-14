"""
LSP: Subtypes are substitutable; flying is separated via interface.
"""
from abc import ABC, abstractmethod


class Bird(ABC):
    @abstractmethod
    def make_sound(self) -> None:
        pass


class FlyingBird(ABC):
    @abstractmethod
    def fly(self) -> None:
        pass


class Sparrow(Bird, FlyingBird):
    def make_sound(self) -> None:
        print("Chirp Chirp!")

    def fly(self) -> None:
        print("Sparrow is flying...")


class Penguin(Bird):
    def make_sound(self) -> None:
        print("Penguin sound...")


def main():
    sparrow: Bird = Sparrow()
    penguin: Bird = Penguin()

    sparrow.make_sound()
    penguin.make_sound()

    flying_bird: FlyingBird = Sparrow()
    flying_bird.fly()  # Works without breaking substitution


if __name__ == "__main__":
    main()
