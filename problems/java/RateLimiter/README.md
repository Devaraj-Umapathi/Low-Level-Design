# Rate Limiter – Low Level Design (LLD)

A multi-algorithm rate limiting library implemented in Java. It throttles requests per key (for example per user or client) using several classic algorithms, selected at construction time via a factory.

---

## 1. Problem Analysis & Requirements Breakdown

### What is a Rate Limiter?

**Definition:** A rate limiter is a component that decides whether an incoming request should be **allowed** or **rejected** based on how many requests have been seen recently for a given key. It protects APIs and services from overload and abuse.

**Real-world analogy:** Like a theme-park entry gate:

| Rate limiting concept | Theme-park analogy        |
|-----------------------|---------------------------|
| Request               | Guest arriving at gate    |
| Key (user id, IP)     | Which guest line / pass   |
| Limit (capacity)      | How many entries allowed  |
| Window / refill rate  | How fast entries reset    |
| Rejection             | “Come back in a moment”   |

### What Are We Building?

- **Caller asks:** “May this key make a request now?”
- **System answers:** `true` (allowed) or `false` (rejected).
- **Multiple algorithms** behind one interface so you can compare behaviour or swap strategies.

**Hidden complexities:**

- **Per-key state** – Each user has its own bucket or window.
- **Time-based refill / leak** – Tokens or queue entries age out based on elapsed time.
- **Concurrency** – Many threads may hit the same key; state updates must stay consistent.
- **Algorithm trade-offs** – Burst tolerance, memory use, and boundary effects differ by algorithm.

### Core Requirements

| Requirement              | Description                                      | Why it matters              |
|--------------------------|--------------------------------------------------|-----------------------------|
| Per-key limiting         | State keyed by string (e.g. user id)             | Typical API usage           |
| Pluggable algorithms     | Token bucket, leaky bucket, fixed/sliding windows| Compare and extend          |
| Single decision API      | `allowRequest(key)` → boolean                    | Simple integration          |
| Factory-based creation   | Choose algorithm + parameters in one place       | Open/closed friendly        |
| Thread-safe per-key ops  | Safe updates under concurrent calls              | Realistic service usage     |

### High-Level Flow

```text
Client calls allowRequest(key)
  -> Service delegates to concrete RateLimiter (from Factory)
  -> Limiter finds or creates per-key state (bucket/window)
  -> Limiter applies algorithm (refill/leak/prune/count)
  -> Return true if under limit, false otherwise
```

### Design Goals

- **Uniform API** – All algorithms implement the same `RateLimiter` interface.
- **Easy extension** – New algorithm = new class + factory case + enum value.
- **Interview-friendly** – Shows Strategy + Factory and classic rate-limiting ideas.

**Patterns used:** Strategy (`RateLimiter` + implementations), Factory (`RateLimiterFactory`).

---

## 2. Actors & Use Cases

### Actors (who uses the component from outside)

- **API gateway / service** – Calls the limiter before forwarding work.
- **Client application** – May be the one invoking `allowRequest` for a local limit.

**Not actors:** `TokenBucket`, `FixedWindow`, etc. are **implementations**, not external users.

### Use Cases

| Use case           | Actor   | Description                         | Success        | Failure        |
|--------------------|---------|-------------------------------------|----------------|----------------|
| Check request      | Service | Ask if key may proceed              | true / false   | (none thrown)  |
| Switch algorithm   | Dev/Ops | Construct different `RateLimiterService` | New behaviour | Invalid enum   |

### Allow Request – Step-by-step (this codebase)

1. Client calls `RateLimiterService.allowRequest(key)`.
2. Service forwards to the `RateLimiter` instance created by `RateLimiterFactory.create(type, limit, rateOrWindowSeconds)`.
3. Concrete limiter uses `ConcurrentHashMap` to get or create per-key inner state (`Bucket` / `Window`).
4. Inner state’s synchronized method updates counters or queues based on current time and returns whether the request is allowed.

---

## 3. Core Entities & Responsibilities

### Package layout

```text
src/
├── enums/          RateLimiterType
├── factory/        RateLimiterFactory
├── limiters/       RateLimiter (interface), TokenBucket, LeakyBucket,
│                   FixedWindow, SlidingWindowLog, SlidingWindowCounter
├── service/        RateLimiterService
└── Main            Demo driver
```

### 1. RateLimiter (strategy interface)

- **Behaviour:** `boolean allowRequest(String key)`.
- **Responsibility:** Contract for all algorithms.

### 2. RateLimiterService (facade)

- **State:** Single `RateLimiter` instance.
- **Behaviour:** Constructor `(RateLimiterType type, int limit, int rateOrWindowSeconds)`; `allowRequest(key)`.
- **Responsibility:** Thin wrapper so clients depend on the service type + enum, not concrete limiter classes.

### 3. RateLimiterFactory

- **Behaviour:** `create(type, limit, rateOrWindowSeconds)` maps enum to concrete class.
- **Parameter meaning:** `limit` is always the main capacity / max requests; `rateOrWindowSeconds` meaning depends on type (see below).

### 4. Algorithm implementations (per-key inner state)

| Class                 | `limit`              | `rateOrWindowSeconds`     | Idea |
|-----------------------|----------------------|---------------------------|------|
| **TokenBucket**       | Bucket capacity      | Refill rate (tokens/sec)  | Burst up to capacity; tokens refill continuously. |
| **LeakyBucket**       | Queue capacity       | Leak rate (requests/sec)  | Smooth output; drops when queue full after leak. |
| **FixedWindow**       | Max requests/window  | Window length (seconds)   | Counter resets each window; spike at boundaries possible. |
| **SlidingWindowLog**  | Max requests/window  | Window length (seconds)   | Timestamps in window; accurate, memory ∝ recent requests. |
| **SlidingWindowCounter** | Max requests (approx) | Window length (seconds) | Weighted count across current + previous window. |

Each implementation stores `ConcurrentHashMap<String, InnerState>` and synchronizes on the inner object for `tryConsume` / `tryAcquire` logic.

---

## 4. Relationships & Associations

| Relationship    | Example                                      | Meaning                          |
|-----------------|----------------------------------------------|----------------------------------|
| Implementation  | `TokenBucket` implements `RateLimiter`       | Strategy                         |
| Dependency      | `RateLimiterService` → `RateLimiter`       | Service uses interface           |
| Creation        | `RateLimiterFactory` → concrete limiters     | Factory                          |
| Composition     | `TokenBucket` → map of `Bucket`            | Limiter owns per-key state       |

---

## 5. Design Patterns Used

### 5.1 Strategy – Rate limiting algorithms

- **Interface:** `RateLimiter`.
- **Implementations:** Token bucket, leaky bucket, fixed window, sliding window log, sliding window counter.
- **Benefit:** Swap algorithm without changing `RateLimiterService` callers.

### 5.2 Factory – Algorithm selection

- **Class:** `RateLimiterFactory.create(RateLimiterType, int, int)`.
- **Benefit:** Central mapping from configuration (enum + numbers) to instances; no `switch` in client code.

---

## 6. Thread Safety & Concurrency

### Approach in this codebase

- **Per-key maps:** `ConcurrentHashMap` for lazy creation of inner `Bucket` / `Window` instances.
- **Critical sections:** Inner classes use `synchronized` methods (`tryConsume`, `tryAcquire`, `tryAcquire`) so token refill, queue leak, and counter updates are atomic per key.

### Implications

- Different keys can progress in parallel on different bucket locks.
- Same key is serialized through that key’s inner lock, which matches typical “one decision stream per user” usage.

---

## 7. API Design Choices

### Boolean vs exception

- **allowRequest** returns `false` when throttled instead of throwing. Callers can log, return HTTP 429, or queue work without exception-driven flow.

### Factory parameters

- One `limit` and one `rateOrWindowSeconds` keeps the demo API small; production systems often expose named parameters (burst, sustained rate, window) per algorithm.

### Encapsulation

- Inner `Bucket` / `Window` classes are private; clients only see `RateLimiter` / `RateLimiterService`.

---

## 8. How to Run

**Prerequisites:** Java 17+ (or compatible JDK).

**Compile:**

```bash
cd problems/java/RateLimiter
mkdir -p out
javac -d out src/enums/*.java src/factory/*.java src/limiters/*.java src/service/*.java src/Main.java
```

**Run:**

```bash
java -cp out Main
```

**Example client usage:**

```java
import enums.RateLimiterType;
import service.RateLimiterService;

RateLimiterService svc = new RateLimiterService(
    RateLimiterType.SLIDING_WINDOW_LOG,
    5,   // max 5 requests
    5    // per 5 second window
);
boolean ok = svc.allowRequest("user-123");
```

---

## 9. End-to-End Flow (summary)

1. Construct `RateLimiterService` with type and numeric parameters.
2. Factory builds the matching `RateLimiter` implementation.
3. Each `allowRequest(key)` resolves per-key state, applies time-based rules, and returns allow/deny.

---

## 10. Extensibility

| Change                    | Effort | How |
|---------------------------|--------|-----|
| New algorithm             | Low    | New class implementing `RateLimiter`; add enum value; extend factory switch. |
| Distributed limiting      | High   | Replace in-memory map with Redis (or similar) + Lua/scripts per algorithm. |
| Metrics / logging         | Low    | Wrap `RateLimiter` or add hooks in `RateLimiterService`. |
| Named configuration       | Medium | Builder or config DTO per algorithm instead of two ints. |

---

## 11. Summary

- **Entities:** `RateLimiter` (interface), five concrete limiters, `RateLimiterFactory`, `RateLimiterService`, `RateLimiterType`.
- **Patterns:** Strategy (algorithms), Factory (creation).
- **Concurrency:** `ConcurrentHashMap` + per-key `synchronized` inner state.
- **Scope:** In-process, per-key limiting; suitable for LLD and local demos.

This README reflects the current Java codebase in this repository. A Python port lives under [problems/python/RateLimiter](../../python/RateLimiter).
