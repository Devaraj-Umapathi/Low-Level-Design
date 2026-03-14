"""
Singleton using Enum - simple, serialization & reflection safe.
"""
from enum import Enum


class Singleton(Enum):
    INSTANCE = 1

    def show_message(self) -> None:
        print("Singleton using Enum!")


def main():
    Singleton.INSTANCE.show_message()


if __name__ == "__main__":
    main()
