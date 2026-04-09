package limiters;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucket implements RateLimiter{

    private final int capacity;
    private final int refillRate;

    // user Id -> 5 req 1 sec
    // 1 -> 5 req 1sec
    // 2 -> 5 req 1sec
    private ConcurrentHashMap<String, Bucket> buckets;

    public TokenBucket(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        buckets = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String key) {
        Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(capacity, refillRate));
        return bucket.tryConsume();
    }

    private static class Bucket {
        private final int capacity; // 10
        private final int refillRate; // 5
        private int tokens;
        private long lastRefillTime;

        public Bucket(int capacity, int refillRate) {
            this.capacity = capacity;
            this.refillRate = refillRate;
            this.tokens = capacity;
            lastRefillTime = System.currentTimeMillis();
        }

        public synchronized boolean tryConsume() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        // 1 sec 5 req
        // 10 sec -> 10 * 5 = 50
        // cap = 5
        private void refill() {
            long now = System.currentTimeMillis();
            double timeElapsedInSeconds = (double) (now - lastRefillTime) / 1000;
            int tokenToAdd = (int)(timeElapsedInSeconds * refillRate);
            System.out.println(now+"----"+lastRefillTime+"---"+timeElapsedInSeconds+"---"+refillRate+"---"+tokenToAdd);
            tokens = Math.min(capacity, tokens + tokenToAdd);
            if (tokenToAdd > 0) lastRefillTime = now;
        }
    }
}
