package limiters;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindow implements RateLimiter{
    private final int maxRequest;
    private final int windowSize;

    private ConcurrentHashMap<String, Window> windows;

    public FixedWindow(int maxRequest, int windowSize) {
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
        private long windowStartTime;
        private int counter;

        public Window(int maxRequest, int windowSize) {
            this.maxRequest = maxRequest;
            this.windowSizeMs = windowSize * 1000;
            counter = 0;
            windowStartTime = System.currentTimeMillis();
        }

        public synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();

            if (now - windowStartTime >= windowSizeMs) {
                counter = 0;
                windowStartTime = now;
            }

            if (counter < maxRequest) {
                counter++;
                return true;
            }
            return false;
        }
    }
}
