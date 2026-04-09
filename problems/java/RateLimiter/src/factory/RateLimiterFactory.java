package factory;

import enums.RateLimiterType;
import limiters.FixedWindow;
import limiters.LeakyBucket;
import limiters.RateLimiter;
import limiters.SlidingWindowCounter;
import limiters.SlidingWindowLog;
import limiters.TokenBucket;

public final class RateLimiterFactory {
    private RateLimiterFactory() {
    }

    public static RateLimiter create(RateLimiterType type, int limit, int rateOrWindowSeconds) {
        switch (type) {
            case TOKEN_BUCKET:
                return new TokenBucket(limit, rateOrWindowSeconds);
            case LEAKY_BUCKET:
                return new LeakyBucket(limit, rateOrWindowSeconds);
            case FIXED_WINDOW:
                return new FixedWindow(limit, rateOrWindowSeconds);
            case SLIDING_WINDOW_LOG:
                return new SlidingWindowLog(limit, rateOrWindowSeconds);
            case SLIDING_WINDOW_COUNTER:
                return new SlidingWindowCounter(limit, rateOrWindowSeconds);
            default:
                throw new IllegalArgumentException("Unsupported rate limiter type: " + type);
        }
    }
}
