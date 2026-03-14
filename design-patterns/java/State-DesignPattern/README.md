# State Design Pattern

## Overview

The State Design Pattern is a behavioral design pattern that allows an object to change its behavior when its internal state changes. Instead of using large conditional statements such as `if-else` or `switch` blocks to handle different behaviors, the state pattern encapsulates state-specific behavior into separate classes.

In this pattern, the object (called the **Context**) delegates the behavior to a **State object** that represents its current state. When the state changes, the behavior of the context changes automatically.

This pattern helps organize complex conditional logic into smaller, maintainable classes.

---

## Key Idea

Rather than writing logic like:

```
if(state == STATE_A) {
    // behavior A
} else if(state == STATE_B) {
    // behavior B
} else if(state == STATE_C) {
    // behavior C
}
```

The State Pattern moves each behavior into separate classes:

```
StateA
StateB
StateC
```

The context object simply delegates the work to the current state.

---

## Components of the State Pattern

### 1. Context

The main object whose behavior changes depending on its state. It maintains a reference to the current state object.

Example:

* ATM Machine
* Media Player
* Order Processing System

### 2. State Interface

Defines the common interface that all concrete states must implement.

### 3. Concrete States

Classes that implement the state interface and define behavior for a specific state.

---

## When to Use the State Pattern

Use the State Pattern when:

### 1. Object Behavior Depends on Its State

When an object behaves differently depending on its internal state.

Example:

* ATM machine states (Idle, Card Inserted, Authenticated)
* Media player states (Playing, Paused, Stopped)

### 2. Large Conditional Statements Exist

If your code contains large `if-else` or `switch` statements based on object state.

The State Pattern helps remove these conditionals and distribute logic into state classes.

### 3. State Transitions Are Frequent

If the object frequently changes its state during execution.

Example:

* Order lifecycle
* Workflow engines
* Game character states

### 4. You Want to Follow Open/Closed Principle

Adding new states should not require modifying existing logic.

With the State Pattern, new states can be added by simply creating new classes.

---

## Real World Examples

Common systems where the State Pattern is used:

* ATM Machine
* Traffic Signal System
* Order Processing Systems
* Media Players
* Game Character Behavior

Example ATM states:

```
Idle -> Card Inserted -> Authenticated -> Cash Dispensed
```

Each state controls what actions are allowed.

---

## Benefits

### Cleaner Code

Removes complex conditional logic.

### Better Maintainability

Each state has its own class which makes the system easier to understand.

### Extensibility

New states can be added without modifying existing code.

### Single Responsibility

Each state class handles behavior for only one state.

---

## Summary

The State Design Pattern helps manage objects whose behavior changes depending on their internal state. By encapsulating state-specific behavior into separate classes, the pattern removes complex conditional logic and makes the system easier to maintain, extend, and understand.
