# Adapter Design Pattern

## 👓 Overview

The **Adapter Design Pattern** is a structural pattern used to allow incompatible interfaces to work together. It acts as a bridge between two different interfaces — converting one interface into another that a client expects.

---

## 📦 Real-World Analogy

Imagine you're from **India** and you're traveling to the **USA**. Your Indian phone charger has **3 round pins**, but US sockets support **2 flat pins**.

Your charger **won’t fit**, so you use a **travel adapter** that:

- Accepts **US socket input**
- Allows you to **plug in your Indian charger**

This is the Adapter Pattern in action — it makes one thing compatible with another without changing the actual devices.

---

## 💻 Programming Analogy

Let’s say you have a **legacy system** (or a foreign object) that doesn’t match your modern application’s expected interface.

Instead of rewriting it, you wrap it in an **adapter** that translates method calls.

---

## 🧱 Participants

| Role              | Description                                                |
|-------------------|------------------------------------------------------------|
| **Target**        | Interface expected by the client                           |
| **Client**        | Uses the target interface                                  |
| **Adaptee**       | Existing/legacy class with an incompatible interface       |
| **Adapter**       | Converts the adaptee's interface into the target interface |

---

## 📌 Key Points
- Use composition in the adapter to hold an instance of the adaptee.
- The adapter implements the target interface and delegates method calls to the adaptee.
- You can use adapter to work with third-party APIs, legacy code, or incompatible libraries.

---

## 🧠 When to Use
- When you want to integrate an existing class into a system but its interface doesn’t match.
- When you can’t (or shouldn’t) change the source code of the legacy or third-party class.
- When you want to wrap a class to provide a more modern or unified interface.

---