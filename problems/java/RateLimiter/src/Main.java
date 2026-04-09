import enums.RateLimiterType;
import service.RateLimiterService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║     RATE LIMITER ALGORITHMS DEMONSTRATION         ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demoLimiter("TOKEN_BUCKET", RateLimiterType.TOKEN_BUCKET, 5, 2);
        demoLimiter("LEAKY_BUCKET", RateLimiterType.LEAKY_BUCKET, 5, 2);
        demoLimiter("FIXED_WINDOW", RateLimiterType.FIXED_WINDOW, 5, 5);
        demoLimiter("SLIDING_WINDOW_LOG", RateLimiterType.SLIDING_WINDOW_LOG, 5, 5);
        demoLimiter("SLIDING_WINDOW_COUNTER", RateLimiterType.SLIDING_WINDOW_COUNTER, 5, 5);
    }

    private static void demoLimiter(String name, RateLimiterType type, int limit, int rateOrWindowSeconds) {
        System.out.println("\n--- " + name + " ---");
        RateLimiterService limiterService = new RateLimiterService(type, limit, rateOrWindowSeconds);
        String userId = "user-123";

        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiterService.allowRequest(userId);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }
    }
}
