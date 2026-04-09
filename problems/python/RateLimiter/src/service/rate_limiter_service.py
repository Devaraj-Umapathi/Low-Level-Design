"""Facade over a concrete rate limiter strategy."""
from enums.rate_limiter_type import RateLimiterType
from factory.rate_limiter_factory import RateLimiterFactory
from limiters.rate_limiter import RateLimiter


class RateLimiterService:
    def __init__(
        self,
        limiter_type: RateLimiterType,
        limit: int,
        rate_or_window_seconds: int,
    ) -> None:
        self._rate_limiter: RateLimiter = RateLimiterFactory.create(
            limiter_type, limit, rate_or_window_seconds
        )

    def allow_request(self, key: str) -> bool:
        return self._rate_limiter.allow_request(key)
