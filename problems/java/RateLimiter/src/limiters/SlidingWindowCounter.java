package limiters;

import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounter implements RateLimiter {

    private final int maxRequest;
    private final int windowSize;

    private ConcurrentHashMap<String, Window> windows;

    public SlidingWindowCounter(int maxRequest, int windowSize) {
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
        private final int windowSizeMs;
        private int previousWindowCount;
        private int currentWindowCount;
        private long currentWindowStartTime;

        public Window(int maxRequest, int windowSize) {
            this.maxRequest = maxRequest;
            this.windowSizeMs = windowSize * 1000;
            previousWindowCount = 0;
            currentWindowCount = 0;
            currentWindowStartTime = System.currentTimeMillis();
        }

        public synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();
            long timeElapsedInCurrentWindow = now - currentWindowStartTime;

            while (timeElapsedInCurrentWindow >= windowSizeMs) {
                previousWindowCount = currentWindowCount;
                currentWindowCount = 0;
                currentWindowStartTime += windowSizeMs;
                timeElapsedInCurrentWindow = now - currentWindowStartTime;
            }

            double currentWindowProgress = (double) timeElapsedInCurrentWindow / windowSizeMs;
            double previousWindowWeight = 1 - currentWindowProgress;
            double weight = (previousWindowCount * previousWindowWeight) + currentWindowCount;

            if (weight < maxRequest) {
                currentWindowCount++;
                return  true;
            }
            return false;
        }
    }
}
