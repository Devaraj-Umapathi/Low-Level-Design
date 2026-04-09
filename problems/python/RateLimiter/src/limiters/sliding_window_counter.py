"""Sliding window counter (weighted) rate limiter."""
import threading
import time
from typing import Dict


class SlidingWindowCounter:
    def __init__(self, max_request: int, window_size_seconds: int) -> None:
        self._max_request = max_request
        self._window_size = window_size_seconds
        self._windows: Dict[str, "SlidingWindowCounter._Window"] = {}
        self._map_lock = threading.Lock()

    def allow_request(self, key: str) -> bool:
        with self._map_lock:
            if key not in self._windows:
                self._windows[key] = SlidingWindowCounter._Window(
                    self._max_request, self._window_size
                )
            window = self._windows[key]
        return window.try_acquire()

    class _Window:
        def __init__(self, max_request: int, window_size: int) -> None:
            self._max_request = max_request
            self._window_size_ms = window_size * 1000
            self._previous_window_count = 0
            self._current_window_count = 0
            self._current_window_start_time = time.time() * 1000
            self._lock = threading.Lock()

        def try_acquire(self) -> bool:
            with self._lock:
                now = time.time() * 1000
                elapsed = now - self._current_window_start_time
                while elapsed >= self._window_size_ms:
                    self._previous_window_count = self._current_window_count
                    self._current_window_count = 0
                    self._current_window_start_time += self._window_size_ms
                    elapsed = now - self._current_window_start_time
                progress = elapsed / self._window_size_ms
                previous_weight = 1.0 - progress
                weight = (
                    self._previous_window_count * previous_weight
                    + self._current_window_count
                )
                if weight < self._max_request:
                    self._current_window_count += 1
                    return True
                return False
