"""Fixed window counter rate limiter."""
import threading
import time
from typing import Dict


class FixedWindow:
    def __init__(self, max_request: int, window_size_seconds: int) -> None:
        self._max_request = max_request
        self._window_size = window_size_seconds
        self._windows: Dict[str, "FixedWindow._Window"] = {}
        self._map_lock = threading.Lock()

    def allow_request(self, key: str) -> bool:
        with self._map_lock:
            if key not in self._windows:
                self._windows[key] = FixedWindow._Window(
                    self._max_request, self._window_size
                )
            window = self._windows[key]
        return window.try_acquire()

    class _Window:
        def __init__(self, max_request: int, window_size: int) -> None:
            self._max_request = max_request
            self._window_size_ms = window_size * 1000
            self._counter = 0
            self._window_start_time = time.time() * 1000
            self._lock = threading.Lock()

        def try_acquire(self) -> bool:
            with self._lock:
                now = time.time() * 1000
                if now - self._window_start_time >= self._window_size_ms:
                    self._counter = 0
                    self._window_start_time = now
                if self._counter < self._max_request:
                    self._counter += 1
                    return True
                return False
