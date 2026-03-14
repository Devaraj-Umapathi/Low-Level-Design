"""
Eager Singleton - thread-safe, created at class definition / first use.
"""
from typing import ClassVar


class Singleton:
    _instance: ClassVar["Singleton | None"] = None

    def __new__(cls) -> "Singleton":
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance


def main():
    print("Requesting instance the first time:")
    obj1 = Singleton()
    print("Requesting instance the second time:")
    obj2 = Singleton()
    print("Are both instances same?", obj1 is obj2)


if __name__ == "__main__":
    main()
