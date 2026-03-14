"""
Builder Pattern - With Builder (Python)
Fluent builder for Computer.
"""


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

    class ComputerBuilder:
        def __init__(self, hdd: str, ram: str):
            self._hdd = hdd
            self._ram = ram
            self._graphics_card: str | None = None
            self._bluetooth: str | None = None

        def enable_graphics_card(self, graphics_card: str) -> "Computer.ComputerBuilder":
            self._graphics_card = graphics_card
            return self

        def enable_bluetooth(self, bluetooth: str) -> "Computer.ComputerBuilder":
            self._bluetooth = bluetooth
            return self

        def build(self) -> "Computer":
            return Computer(
                self._hdd, self._ram, self._graphics_card, self._bluetooth
            )


def main():
    gaming_computer = (
        Computer.ComputerBuilder("1 TB", "16 GB")
        .enable_graphics_card("ROG")
        .enable_bluetooth("SONY")
        .build()
    )
    print(gaming_computer)

    basic_computer = (
        Computer.ComputerBuilder("500 GB", "8 GB").enable_bluetooth("SONY").build()
    )
    print(basic_computer)


if __name__ == "__main__":
    main()
