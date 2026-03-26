"""Dedicated demo entrypoint for Builder + Director example."""
from with_builder_and_director.computer import ComputerDirector


def main() -> None:
    director = ComputerDirector()

    gaming_computer = director.construct_gaming_computer()
    print("Gaming Computer:", gaming_computer)

    office_computer = director.construct_office_computer()
    print("Office Computer:", office_computer)


if __name__ == "__main__":
    main()
