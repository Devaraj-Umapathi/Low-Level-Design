"""
Thread-safe Singleton using lock.
"""
import threading
from typing import ClassVar


class Singleton:
    _instance: ClassVar["Singleton | None"] = None
    _lock: ClassVar[threading.Lock] = threading.Lock()

    def __new__(cls) -> "Singleton":
        with cls._lock:
            if cls._instance is None:
                cls._instance = super().__new__(cls)
        return cls._instance


def main():
    def task():
        instance = Singleton()
        print(f"{threading.current_thread().name} → {id(instance)}")

    t1 = threading.Thread(target=task, name="Thread-1")
    t2 = threading.Thread(target=task, name="Thread-2")
    t1.start()
    t2.start()
    t1.join()
    t2.join()


if __name__ == "__main__":
    main()
