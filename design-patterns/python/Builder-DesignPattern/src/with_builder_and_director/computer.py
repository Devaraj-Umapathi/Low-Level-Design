"""
Builder Pattern - With Builder and Director (Python)
Builder interface, concrete builders, director.
"""
from abc import ABC, abstractmethod


class Computer:
    def __init__(
        self,
        hdd: str,
        ram: str,
        graphics_card: str | None,
        bluetooth: str | None,
    ):
        self.hdd = hdd
        self.ram = ram
        self.graphics_card = graphics_card
        self.bluetooth = bluetooth

    def __str__(self) -> str:
        return (
            f"Computer [CPU={self.hdd}, RAM={self.ram}, "
            f"GraphicsCardEnabled={self.graphics_card}, BluetoothEnabled={self.bluetooth}]"
        )


class ComputerBuilder(ABC):
    @abstractmethod
    def enable_graphics_card(self, graphics_card: str) -> "ComputerBuilder":
        pass

    @abstractmethod
    def enable_bluetooth(self, bluetooth: str) -> "ComputerBuilder":
        pass

    @abstractmethod
    def build(self) -> Computer:
        pass


class GamingComputerBuilder(ComputerBuilder):
    def __init__(self, hdd: str, ram: str):
        self._hdd = hdd
        self._ram = ram
        self._graphics_card: str | None = None
        self._bluetooth: str | None = None

    def enable_graphics_card(self, graphics_card: str) -> ComputerBuilder:
        self._graphics_card = graphics_card
        return self

    def enable_bluetooth(self, bluetooth: str) -> ComputerBuilder:
        self._bluetooth = bluetooth
        return self

    def build(self) -> Computer:
        return Computer(self._hdd, self._ram, self._graphics_card, self._bluetooth)


class OfficeComputerBuilder(ComputerBuilder):
    def __init__(self, hdd: str, ram: str):
        self._hdd = hdd
        self._ram = ram
        self._graphics_card: str | None = None
        self._bluetooth: str | None = None

    def enable_graphics_card(self, graphics_card: str) -> ComputerBuilder:
        self._graphics_card = graphics_card
        return self

    def enable_bluetooth(self, bluetooth: str) -> ComputerBuilder:
        self._bluetooth = bluetooth
        return self

    def build(self) -> Computer:
        return Computer(self._hdd, self._ram, self._graphics_card, self._bluetooth)


class ComputerDirector:
    def construct_gaming_computer(self) -> Computer:
        return (
            GamingComputerBuilder("1 TB", "16 GB")
            .enable_graphics_card("ROG")
            .enable_bluetooth("SONY")
            .build()
        )

    def construct_office_computer(self) -> Computer:
        return (
            OfficeComputerBuilder("500 GB", "8 GB").enable_bluetooth("SONY").build()
        )


def main():
    director = ComputerDirector()

    gaming_computer = director.construct_gaming_computer()
    print("Gaming Computer:", gaming_computer)

    office_computer = director.construct_office_computer()
    print("Office Computer:", office_computer)


if __name__ == "__main__":
    main()
