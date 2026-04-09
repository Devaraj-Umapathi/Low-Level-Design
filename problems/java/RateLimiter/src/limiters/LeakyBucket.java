package limiters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucket implements RateLimiter{

    private final int capacity;
    private final int leakRate;

    private ConcurrentHashMap<String, Bucket> buckets;

    public LeakyBucket(int capacity, int leakRate) {
        this.capacity = capacity;
        this.leakRate = leakRate;
        buckets = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String key) {
        Bucket bucket = buckets.computeIfAbsent(key, k -> new Bucket(capacity, leakRate));
        return bucket.tryConsume();
    }

    private static class Bucket {
        private final int capacity;
        private final int leakRate;
        private Deque<Long> queue;
        private long lastRefillTime;

        public Bucket(int capacity, int leakRate) {
            this.capacity = capacity;
            this.leakRate = leakRate;
            queue = new ArrayDeque<>(capacity);
            lastRefillTime = System.currentTimeMillis();
        }

        public synchronized boolean tryConsume() {
            leak();
            if (queue.size() < capacity) {
                queue.add(System.currentTimeMillis());
                return true;
            }
            return false;
        }

        // 1 sec -. 2 req
        private void leak() {
            long now = System.currentTimeMillis();
            double elapsedTimeInSeconds = (double) (now - lastRefillTime) / 1000;
            // 10 sec -> 10 * 2 = 20 req
            int requestToLeak = (int)(elapsedTimeInSeconds * leakRate);

            for (int i=0; i<requestToLeak && !queue.isEmpty(); i++) {
                queue.poll();
            }

            if (requestToLeak > 0) lastRefillTime = now;
        }
    }
}
