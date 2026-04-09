"""Token bucket rate limiter."""
import threading
import time
from typing import Dict


class TokenBucket:
    def __init__(self, capacity: int, refill_rate: int) -> None:
        self._capacity = capacity
        self._refill_rate = refill_rate
        self._buckets: Dict[str, "TokenBucket._Bucket"] = {}
        self._map_lock = threading.Lock()

    def allow_request(self, key: str) -> bool:
        with self._map_lock:
            if key not in self._buckets:
                self._buckets[key] = TokenBucket._Bucket(
                    self._capacity, self._refill_rate
                )
            bucket = self._buckets[key]
        return bucket.try_consume()

    class _Bucket:
        def __init__(self, capacity: int, refill_rate: int) -> None:
            self._capacity = capacity
            self._refill_rate = refill_rate
            self._tokens = capacity
            self._last_refill_time = time.time() * 1000
            self._lock = threading.Lock()

        def try_consume(self) -> bool:
            with self._lock:
                self._refill()
                if self._tokens > 0:
                    self._tokens -= 1
                    return True
                return False

        def _refill(self) -> None:
            now = time.time() * 1000
            elapsed_sec = (now - self._last_refill_time) / 1000.0
            token_to_add = int(elapsed_sec * self._refill_rate)
            self._tokens = min(self._capacity, self._tokens + token_to_add)
            if token_to_add > 0:
                self._last_refill_time = now
