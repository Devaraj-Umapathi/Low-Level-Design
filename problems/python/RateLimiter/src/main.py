"""Rate limiter algorithms demonstration (Python port of Java Main)."""
from enums.rate_limiter_type import RateLimiterType
from service.rate_limiter_service import RateLimiterService


def _demo_limiter(
    name: str,
    limiter_type: RateLimiterType,
    limit: int,
    rate_or_window_seconds: int,
) -> None:
    print(f"\n--- {name} ---")
    limiter_service = RateLimiterService(
        limiter_type, limit, rate_or_window_seconds
    )
    user_id = "user-123"
    for i in range(1, 8):
        allowed = limiter_service.allow_request(user_id)
        status = "ALLOWED" if allowed else "REJECTED"
        print(f"Request {i}: {status}")


def main() -> None:
    print("╔═══════════════════════════════════════════════════╗")
    print("║     RATE LIMITER ALGORITHMS DEMONSTRATION         ║")
    print("╚═══════════════════════════════════════════════════╝\n")

    _demo_limiter("TOKEN_BUCKET", RateLimiterType.TOKEN_BUCKET, 5, 2)
    _demo_limiter("LEAKY_BUCKET", RateLimiterType.LEAKY_BUCKET, 5, 2)
    _demo_limiter("FIXED_WINDOW", RateLimiterType.FIXED_WINDOW, 5, 5)
    _demo_limiter(
        "SLIDING_WINDOW_LOG", RateLimiterType.SLIDING_WINDOW_LOG, 5, 5
    )
    _demo_limiter(
        "SLIDING_WINDOW_COUNTER",
        RateLimiterType.SLIDING_WINDOW_COUNTER,
        5,
        5,
    )


if __name__ == "__main__":
    main()
