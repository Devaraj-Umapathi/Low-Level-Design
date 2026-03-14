"""
Bill Pugh Singleton - lazy, thread-safe via nested class.
"""


class Singleton:
    class _SingletonHelper:
        _instance: "Singleton | None" = None

    def __new__(cls) -> "Singleton":
        if Singleton._SingletonHelper._instance is None:
            Singleton._SingletonHelper._instance = object.__new__(cls)
            print("Singleton instance created")
        return Singleton._SingletonHelper._instance


def main():
    print("Calling get_instance first time:")
    s1 = Singleton()
    print("Calling get_instance second time:")
    s2 = Singleton()
    print("Are both instances same?", s1 is s2)


if __name__ == "__main__":
    main()
