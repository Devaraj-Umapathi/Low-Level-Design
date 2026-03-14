"""
Basic Singleton - not thread-safe.
"""
from typing import ClassVar


class Singleton:
    _instance: ClassVar["Singleton | None"] = None

    def __new__(cls) -> "Singleton":
        if cls._instance is None:
            print("Singleton instance created!")
            cls._instance = super().__new__(cls)
        return cls._instance


def main():
    obj1 = Singleton()
    obj2 = Singleton()
    print(obj1 is obj2)  # True


if __name__ == "__main__":
    main()
