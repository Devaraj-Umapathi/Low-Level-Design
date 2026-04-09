"""Factory mapping algorithm type to concrete rate limiter."""
from enums.rate_limiter_type import RateLimiterType
from limiters.fixed_window import FixedWindow
from limiters.leaky_bucket import LeakyBucket
from limiters.sliding_window_counter import SlidingWindowCounter
from limiters.sliding_window_log import SlidingWindowLog
from limiters.token_bucket import TokenBucket


class RateLimiterFactory:
    @staticmethod
    def create(
        limiter_type: RateLimiterType, limit: int, rate_or_window_seconds: int
    ):
        if limiter_type is RateLimiterType.TOKEN_BUCKET:
            return TokenBucket(limit, rate_or_window_seconds)
        if limiter_type is RateLimiterType.LEAKY_BUCKET:
            return LeakyBucket(limit, rate_or_window_seconds)
        if limiter_type is RateLimiterType.FIXED_WINDOW:
            return FixedWindow(limit, rate_or_window_seconds)
        if limiter_type is RateLimiterType.SLIDING_WINDOW_LOG:
            return SlidingWindowLog(limit, rate_or_window_seconds)
        if limiter_type is RateLimiterType.SLIDING_WINDOW_COUNTER:
            return SlidingWindowCounter(limit, rate_or_window_seconds)
        raise ValueError(f"Unsupported rate limiter type: {limiter_type}")
