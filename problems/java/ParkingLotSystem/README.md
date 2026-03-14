# Parking Lot System – Low Level Design (LLD)

A multi-level parking lot service implemented in Java. It handles vehicle entry, spot assignment, fee calculation, and vehicle exit with support for concurrent access and swappable strategies.

---

## 1. Problem Analysis & Requirements Breakdown

### What is a Parking Lot System?

**Definition:** A parking lot service is a software application that manages parking operations in a multi-level facility. It handles vehicle entry, spot assignment, fee calculation, and vehicle exit while keeping data consistent across multiple entry/exit gates.

**Real-world analogy:** Like a hotel:

| Parking concept   | Hotel analogy        |
|-------------------|----------------------|
| Entry gate        | Check-in desk        |
| Parking spot      | Room                 |
| Parking ticket    | Room key card        |
| Exit gate         | Check-out desk       |
| Floors            | Hotel floors         |
| Vehicle sizes     | Room types (single, double, suite) |

### What Are We Building?

- **You drive in** → System finds a parking spot.
- **You park** → System gives you a ticket.
- **You leave later** → System computes fee and frees the spot.

**Hidden complexities:**

- **Multiple floors** – Ground, Level 1, Level 2, etc.
- **Different vehicle types** – Bikes (SMALL), cars (MEDIUM), trucks (LARGE).
- **Concurrent access** – Many customers at the same time.
- **Flexible pricing** – Flat rate today, tiered or peak-hour tomorrow.
- **Spot selection** – Nearest first, best fit, etc.

### Core Requirements

| Requirement            | Description                                      | Why it matters              |
|------------------------|--------------------------------------------------|-----------------------------|
| Multi-level structure  | Multiple floors, each with multiple spots       | Real lots have levels       |
| Vehicle size handling | Bikes, cars, trucks need appropriate spots      | Size rules (spot ≥ vehicle) |
| Spot assignment       | Find and assign an available spot on entry      | Core behaviour              |
| Ticketing             | Issue ticket on entry; link vehicle ↔ spot      | Know who is where           |
| Fee calculation       | Charge based on duration                        | Revenue                     |
| Concurrent access     | Multiple gates at same time                     | Real-world usage            |
| Flexible strategies   | Change fee and spot-selection algorithms        | Business changes            |

### High-Level Flow

```
Customer arrives → System finds spot (ParkingStrategy) → Spot available?
  → Yes: Assign spot, create ticket, return ticket
  → No: Throw NoParkingSpotAvailableException

Customer returns with ticket → System validates ticket
  → Valid: Release spot, set end time, calculate fee (FeeStrategy), return fee
  → Invalid: Throw InvalidTicketException
```

### Design Goals

- **Clear responsibilities** – Each class has a focused role.
- **Easy to change** – Fee and spot-selection logic swappable.
- **Safe under concurrency** – No double-booking of spots.
- **Extensible** – New strategies and vehicle types without rewriting core flow.

**Patterns used:** Singleton, Strategy (fee + parking), Factory (vehicles), Builder (lot setup), inheritance (vehicle hierarchy).

---

## 2. Actors & Use Cases

### Actors (who uses the service from outside)

- **Driver/Customer** – Main actor: parks and pays.
- *(Future)* **Parking attendant** – Overrides or special cases.
- *(Future)* **Administrator** – Configures pricing, floors, reports.
- *(Future)* **Payment gateway** – External payment service.

**Not actors:** Vehicle, ParkingSpot, ParkingTicket are **entities** the service manages, not users of the service.

### Use Cases

| Use case          | Actor    | Description              | Success                    | Failure                         |
|-------------------|----------|--------------------------|----------------------------|---------------------------------|
| Park vehicle      | Customer | Enter and get a spot     | Ticket issued              | No spot → exception             |
| Unpark vehicle    | Customer | Present ticket, pay, exit| Fee returned, spot freed   | Invalid ticket → exception      |
| Change fee strategy | Admin  | Update pricing           | Strategy swapped           | Invalid strategy                |
| Add floor         | Admin    | Expand capacity          | Floor added                | Invalid config                  |

### Park Vehicle – Step-by-step (this codebase)

1. Client calls `parkingLotSystem.parkVehicle(vehicle)`.
2. System calls `parkingStrategy.findSpot(floors, vehicle)` (e.g. `NearestFirstStrategy`).
3. Strategy iterates floors/spots, uses `spot.canFitVehicle(vehicle)` (spot free and `spotSize.canFit(vehicleSize)`).
4. If a spot is found: `spot.parkVehicle(vehicle)` (synchronized; may return `null` if another thread took it).
5. If `parkVehicle` returns a ticket: store in `activeTickets`, return ticket.
6. If spot was taken (null): retry with next candidate, up to total spots; else throw `NoParkingSpotAvailableException`.

---

## 3. Core Entities & Responsibilities

### Package layout

```
src/
├── enums/    VehicleSize (SMALL, MEDIUM, LARGE; canFit())
├── model/        ParkingFloor, ParkingSpot, ParkingTicket, Vehicle, Car, Bike, Truck
├── strategy/      FeeStrategy, FlatRateFeeStrategy, ParkingStrategy, NearestFirstStrategy
├── service/       ParkingLotSystem (singleton)
├── builders/     ParkingLotBuilder
├── factories/    VehicleFactory
├── exceptions/   NoParkingSpotAvailableException, InvalidTicketException
└── ParkingLotDemo
```

### 1. ParkingLotSystem (coordinator)

- **State:** `List<ParkingFloor> floors`, `ConcurrentHashMap<String, ParkingTicket> activeTickets`, `FeeStrategy`, `ParkingStrategy`.
- **Behaviour:** `getInstance()`, `addFloor()`, `createBuilder()`, `parkVehicle()`, `unParkVehicle()`, `printFloors()`, `setFeeStrategy()`, `setParkingStrategy()`.
- **Why singleton:** One logical parking lot; all gates must see the same state.

### 2. ParkingFloor

- **State:** `floorNumber`, `List<ParkingSpot> spots` (unmodifiable view from outside).
- **Behaviour:** `getFloorNumber()`, `getSpots()`, `addSpot(size, id)`.
- **Responsibility:** Group spots by floor; expose read-only spot list.

### 3. ParkingSpot

- **State:** `spotId`, `spotSize` (VehicleSize), `isOccupied`, `parkedVehicle`.
- **Behaviour:** `canFitVehicle(vehicle)` (free and `spotSize.canFit(vehicle.getVehicleSize())`), `parkVehicle(vehicle)` (synchronized; returns ticket or null), `unParkVehicle()` (synchronized).
- **Spot fit rule:** A spot fits a vehicle if it is free and spot size ≥ vehicle size (e.g. LARGE spot fits MEDIUM/SMALL).

### 4. ParkingTicket

- **State:** `ticketId` (UUID), `parkedVehicle`, `assignedSpot`, `startTimeStamp`, `endTimeStamp`.
- **Behaviour:** `getDurationInHours()` (uses end time or current time if still parked), `setEndTimeStamp()` (called on exit).
- **Responsibility:** Link vehicle, spot, and duration for fee calculation.

### 5. Vehicle (abstract) and VehicleSize

- **Vehicle:** `vehicleNumber`, `vehicleSize`; subclasses: `Car`, `Bike`, `Truck`.
- **VehicleSize:** `SMALL`, `MEDIUM`, `LARGE`; `canFit(VehicleSize other)` (this.ordinal() >= other.ordinal()).
- **Responsibility:** Represent customer vehicle and size for spot matching.

---

## 4. Relationships & Associations

| Relationship   | Example                                      | Meaning                          |
|----------------|-----------------------------------------------|----------------------------------|
| Composition   | ParkingLotSystem → ParkingFloor → ParkingSpot | System owns floors, floor owns spots. |
| Aggregation   | ParkingLotSystem → FeeStrategy, ParkingStrategy | System uses strategies; can swap.   |
| Association   | ParkingSpot → Vehicle, ParkingTicket → Vehicle/Spot | Temporary reference.          |
| Implementation| FlatRateFeeStrategy implements FeeStrategy   | Implements interface.            |
| Inheritance   | Car / Bike / Truck extend Vehicle             | Is-a relationship.               |

---

## 5. Design Patterns Used

### 5.1 Singleton – ParkingLotSystem

- **Problem:** Multiple instances would mean inconsistent state across gates.
- **Solution:** Single instance via `getInstance()` with double-checked locking and `volatile` for thread-safe creation.
- **Usage:** `ParkingLotSystem.getInstance()` everywhere.

### 5.2 Strategy – Fee calculation

- **Interface:** `FeeStrategy` with `double calculateFee(ParkingTicket)`.
- **Implementation:** `FlatRateFeeStrategy(flatRatePerHour)` – fee = ceil(durationHours) × rate.
- **Benefit:** New pricing (tiered, peak-hour) = new class; no change to `ParkingLotSystem`.

### 5.3 Strategy – Spot selection

- **Interface:** `ParkingStrategy` with `ParkingSpot findSpot(List<ParkingFloor>, Vehicle)`.
- **Implementation:** `NearestFirstStrategy` – first floor, first free spot that fits.
- **Benefit:** Can add BestFitStrategy, RandomStrategy, etc., without changing core logic.

### 5.4 Factory – Vehicle creation

- **Class:** `VehicleFactory.createVehicle(vehicleNumber, VehicleSize)`.
- **Mapping:** SMALL → Bike, MEDIUM → Car, LARGE → Truck.
- **Benefit:** Single place for vehicle creation; client depends on `Vehicle`, not concrete types.

### 5.5 Builder – Lot structure

- **Class:** `ParkingLotBuilder`: `addFloor(number)`, `addSpot(size, spotId)`, `build()`, `buildAndAddTo(service)`.
- **Access:** `ParkingLotBuilder.builder()` or `parkingLotSystem.createBuilder()`.
- **Benefit:** Client never constructs `ParkingFloor` or `ParkingSpot` directly; consistent setup.

### 5.6 Inheritance – Vehicle hierarchy

- **Abstract:** `Vehicle(vehicleNumber, vehicleSize)`; concrete: `Car`, `Bike`, `Truck`.
- **Benefit:** Polymorphism – `parkVehicle(Vehicle)` works for any vehicle type; shared fields and behaviour in one place.

---

## 6. Thread Safety & Concurrency

### Risk: two threads “find” the same spot

- Both call `findSpot()` and get the same free spot.
- Without care, both could “park” in it → double booking.

### Approach: per-spot lock + retry

1. **ParkingSpot.parkVehicle(vehicle)** is **synchronized**.
   - Inside lock: if `canFitVehicle(vehicle)` then set occupied, create and return ticket; else return **null**.
2. **ParkingLotSystem.parkVehicle(vehicle)**:
   - Loop (up to total number of spots): get candidate from strategy; if null throw `NoParkingSpotAvailableException`; call `spot.parkVehicle(vehicle)`; if non-null, put ticket in `activeTickets` and return; otherwise retry (spot was taken by another thread).

So only one thread can successfully park in a given spot; the other gets `null` and tries another spot.

### Other concurrency details

- **activeTickets:** `ConcurrentHashMap` for concurrent put/remove.
- **Singleton:** `volatile` + double-checked locking so only one instance is created under concurrency.
- **unParkVehicle:** Uses spot’s synchronized `unParkVehicle()` so release is safe.

---

## 7. API Design Choices

### Exceptions vs Optional

- **parkVehicle:** Throws `NoParkingSpotAvailableException` when no spot (instead of returning null). Caller must handle “no space” as an exceptional case.
- **unParkVehicle:** Throws `InvalidTicketException` for unknown or already-used ticket (instead of returning 0). Avoids treating “invalid ticket” as “zero fee”.

### Spot size rule

- A spot can fit a vehicle if **spot size ≥ vehicle size** (e.g. LARGE spot fits MEDIUM or SMALL).
- Implemented as `VehicleSize.canFit(vehicleSize)` (ordinal-based) and used in `ParkingSpot.canFitVehicle(vehicle)`.

### Encapsulation

- `ParkingFloor.getSpots()` returns an unmodifiable list.
- Floor/spot creation is hidden behind `ParkingLotBuilder`; client uses `createBuilder().addFloor(...).addSpot(...).buildAndAddTo(service)`.

---

## 8. How to Run

**Prerequisites:** Java (e.g. 17+).

**Compile:**

```bash
javac -d out src/enums/*.java src/exceptions/*.java src/model/*.java src/strategy/*.java src/factories/*.java src/builders/*.java src/service/*.java src/ParkingLotDemo.java
```

**Run:**

```bash
java -cp out ParkingLotDemo
```

**Example client usage:**

```java
ParkingLotSystem service = ParkingLotSystem.getInstance();

// Setup (builder – no direct Floor/Spot construction)
service.createBuilder()
    .addFloor(0)
    .addSpot(VehicleSize.SMALL, "0-A")
    .addSpot(VehicleSize.MEDIUM, "0-B")
    .addFloor(1)
    .addSpot(VehicleSize.MEDIUM, "1-A")
    .buildAndAddTo(service);

// Park
Vehicle car = VehicleFactory.createVehicle("ABC-123", VehicleSize.MEDIUM);
ParkingTicket ticket = service.parkVehicle(car);  // throws if no spot

// Unpark
double fee = service.unParkVehicle(ticket.getTicketId());  // throws if invalid ticket
```

---

## 9. End-to-End Flow (summary)

**Entry:**

1. `parkVehicle(vehicle)` → strategy finds a spot (e.g. nearest that fits).
2. `spot.parkVehicle(vehicle)` (synchronized) claims spot or returns null; if null, retry.
3. Create `ParkingTicket`, store in `activeTickets`, return ticket.

**Exit:**

1. `unParkVehicle(ticketId)` → remove ticket from `activeTickets`; if missing, throw `InvalidTicketException`.
2. `assignedSpot.unParkVehicle()` (synchronized), set ticket end time.
3. `feeStrategy.calculateFee(ticket)` → return fee (e.g. FlatRateFeeStrategy: ceil(hours) × rate).

---

## 10. Extensibility

| Change                 | Effort   | How |
|------------------------|----------|-----|
| New fee strategy      | Low      | New class implementing `FeeStrategy`; `setFeeStrategy(...)`. |
| New parking strategy  | Low      | New class implementing `ParkingStrategy`; `setParkingStrategy(...)`. |
| New vehicle type      | Low      | New size (if needed), new subclass of `Vehicle`, update `VehicleFactory`. |
| Reservation service    | Medium   | New entities (e.g. Reservation, ReservationManager); in `parkVehicle` check reservations then strategy. |
| Payment integration   | Medium   | e.g. `PaymentStrategy` and implementations; call after fee calculation in `unParkVehicle`. |

---

## 11. Summary

- **Entities:** ParkingLotSystem, ParkingFloor, ParkingSpot, ParkingTicket, Vehicle (and sizes).
- **Patterns:** Singleton (service), Strategy (fee + parking), Factory (vehicles), Builder (lot setup), inheritance (vehicles).
- **Concurrency:** Per-spot `synchronized` park/unpark + retry in `parkVehicle`; `ConcurrentHashMap` for tickets; safe singleton creation.
- **API:** Exceptions for “no spot” and “invalid ticket”; builder for setup; strategies and factory for flexibility and extension.

This README reflects the current Java codebase in this repository.
