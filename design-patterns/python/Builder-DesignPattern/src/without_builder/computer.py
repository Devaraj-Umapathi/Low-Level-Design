"""
Builder Pattern - Without Builder (Python)
Telescoping constructors / many optional params.
"""


class Computer:
    def __init__(
        self,
        hdd: str,
        ram: str,
        graphics_card: str | None = None,
        bluetooth: str | None = None,
    ):
        self.hdd = hdd
        self.ram = ram
        self.graphics_card = graphics_card
        self.bluetooth = bluetooth

    def __str__(self) -> str:
        return (
            f"Computer [HDD={self.hdd}, RAM={self.ram}, "
            f"GraphicsCardEnabled={self.graphics_card}, BluetoothEnabled={self.bluetooth}]"
        )


def main():
    computer = Computer("1 TB", "16 GB", "ROG", "SONY")
    print(computer)

    computer2 = Computer("1 TB", "16 GB", None, None)
    computer3 = Computer("1 TB", "16 GB")
    print(computer2)
    print(computer3)


if __name__ == "__main__":
    main()
