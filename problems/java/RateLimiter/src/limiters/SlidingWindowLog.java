package limiters;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowLog implements RateLimiter {
    private final int maxRequest;
    private final int windowSize;

    private ConcurrentHashMap<String, Window> windows;

    public SlidingWindowLog(int maxRequest, int windowSize) {
        this.maxRequest = maxRequest;
        this.windowSize = windowSize;
        windows = new ConcurrentHashMap<>();
    }
    @Override
    public boolean allowRequest(String key) {
        Window window = windows.computeIfAbsent(key, k -> new Window(maxRequest, windowSize));
        return window.tryAcquire();
    }

    private static class Window {
        private final int maxRequest;
        private final int windowSizeInMs;
        private Deque<Long> queue;

        public Window(int maxRequest, int windowSize) {
            this.maxRequest = maxRequest;
            windowSizeInMs = windowSize * 1000;
            queue = new ArrayDeque<>(maxRequest);
        }

        public synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();

            // 1 sec   -> 10 sec = 10 - 1 sec = 9 sec  = 1 sec
            while(!queue.isEmpty() && (now - queue.peek() >= windowSizeInMs)) {
                queue.poll();
            }

            if (queue.size() < maxRequest) {
                queue.offer(now);
                return true;
            }
            return false;
        }
    }
}
