"""Sliding window log rate limiter."""
import threading
import time
from collections import deque
from typing import Deque, Dict


class SlidingWindowLog:
    def __init__(self, max_request: int, window_size_seconds: int) -> None:
        self._max_request = max_request
        self._window_size = window_size_seconds
        self._windows: Dict[str, "SlidingWindowLog._Window"] = {}
        self._map_lock = threading.Lock()

    def allow_request(self, key: str) -> bool:
        with self._map_lock:
            if key not in self._windows:
                self._windows[key] = SlidingWindowLog._Window(
                    self._max_request, self._window_size
                )
            window = self._windows[key]
        return window.try_acquire()

    class _Window:
        def __init__(self, max_request: int, window_size: int) -> None:
            self._max_request = max_request
            self._window_size_ms = window_size * 1000
            self._queue: Deque[float] = deque()
            self._lock = threading.Lock()

        def try_acquire(self) -> bool:
            with self._lock:
                now = time.time() * 1000
                while self._queue and now - self._queue[0] >= self._window_size_ms:
                    self._queue.popleft()
                if len(self._queue) < self._max_request:
                    self._queue.append(now)
                    return True
                return False
