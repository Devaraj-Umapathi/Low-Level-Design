package limiters;

public interface RateLimiter {
    boolean allowRequest(String key);
}
