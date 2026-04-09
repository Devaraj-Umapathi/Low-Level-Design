"""Strategy interface for rate limiting."""
from typing import Protocol


class RateLimiter(Protocol):
    def allow_request(self, key: str) -> bool:
        """Return True if the request is allowed under the limit for key."""
