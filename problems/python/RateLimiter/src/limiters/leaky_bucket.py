"""Leaky bucket rate limiter."""
import threading
import time
from collections import deque
from typing import Deque, Dict


class LeakyBucket:
    def __init__(self, capacity: int, leak_rate: int) -> None:
        self._capacity = capacity
        self._leak_rate = leak_rate
        self._buckets: Dict[str, "LeakyBucket._Bucket"] = {}
        self._map_lock = threading.Lock()

    def allow_request(self, key: str) -> bool:
        with self._map_lock:
            if key not in self._buckets:
                self._buckets[key] = LeakyBucket._Bucket(
                    self._capacity, self._leak_rate
                )
            bucket = self._buckets[key]
        return bucket.try_consume()

    class _Bucket:
        def __init__(self, capacity: int, leak_rate: int) -> None:
            self._capacity = capacity
            self._leak_rate = leak_rate
            self._queue: Deque[float] = deque()
            self._last_refill_time = time.time() * 1000
            self._lock = threading.Lock()

        def try_consume(self) -> bool:
            with self._lock:
                self._leak()
                if len(self._queue) < self._capacity:
                    self._queue.append(time.time() * 1000)
                    return True
                return False

        def _leak(self) -> None:
            now = time.time() * 1000
            elapsed_sec = (now - self._last_refill_time) / 1000.0
            request_to_leak = int(elapsed_sec * self._leak_rate)
            for _ in range(request_to_leak):
                if not self._queue:
                    break
                self._queue.popleft()
            if request_to_leak > 0:
                self._last_refill_time = now
