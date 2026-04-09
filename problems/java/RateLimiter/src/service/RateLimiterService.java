package service;

import enums.RateLimiterType;
import factory.RateLimiterFactory;
import limiters.RateLimiter;

public class RateLimiterService {
    private final RateLimiter rateLimiter;

    public RateLimiterService(RateLimiterType type, int limit, int rateOrWindowSeconds) {
        this.rateLimiter = RateLimiterFactory.create(type, limit, rateOrWindowSeconds);
    }

    public boolean allowRequest(String key) {
        return rateLimiter.allowRequest(key);
    }
}
