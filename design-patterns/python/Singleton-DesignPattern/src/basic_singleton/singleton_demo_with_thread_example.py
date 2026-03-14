"""
Basic singleton (not thread-safe) - demo with two threads.
"""
import threading
from .singleton_demo import Singleton


def main():
    def task() -> None:
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
