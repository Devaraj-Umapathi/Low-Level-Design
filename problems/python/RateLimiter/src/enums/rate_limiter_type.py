"""Rate limiter algorithm identifiers."""
from enum import Enum, auto


class RateLimiterType(Enum):
    TOKEN_BUCKET = auto()
    LEAKY_BUCKET = auto()
    FIXED_WINDOW = auto()
    SLIDING_WINDOW_LOG = auto()
    SLIDING_WINDOW_COUNTER = auto()
