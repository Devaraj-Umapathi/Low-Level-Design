# Rate Limiter – Python LLD

Python port of the Java rate limiter under [problems/java/RateLimiter](../../java/RateLimiter). Same package layout: **Strategy** (`RateLimiter` protocol + implementations), **Factory** (`RateLimiterFactory`), and **Service** (`RateLimiterService`).

For problem analysis, algorithm comparison, and design discussion, see the [Java README](../../java/RateLimiter/README.md).

---

## Layout

```text
src/
├── enums/           rate_limiter_type.py
├── factory/         rate_limiter_factory.py
├── limiters/        token_bucket, leaky_bucket, fixed_window,
│                    sliding_window_log, sliding_window_counter, rate_limiter (Protocol)
├── service/         rate_limiter_service.py
└── main.py          Demo
```

---

## How to run

From this directory:

```bash
cd problems/python/RateLimiter
PYTHONPATH=src python3 src/main.py
```

---

## Notes vs Java

- **API:** `allow_request(key)` uses snake_case; behaviour matches the Java implementations.
- **Concurrency:** Per-key inner state uses `threading.Lock`; map mutations use a short `Lock` when creating a new bucket/window (similar to `ConcurrentHashMap` + `synchronized` inner methods in Java).
- **Time:** Millisecond timestamps use `time.time() * 1000` to mirror `System.currentTimeMillis()`.

This README reflects the Python codebase in this folder.
