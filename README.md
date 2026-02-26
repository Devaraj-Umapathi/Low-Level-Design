# 🎯 What is the Strategy Design Pattern?

The **Strategy Design Pattern** is a behavioral design pattern that allows you to define a family of algorithms, encapsulate each one into separate classes, and make them interchangeable at runtime.

Instead of using multiple `if-else` or `switch` statements to decide behavior, the Strategy Pattern delegates the behavior to separate classes that implement a common interface.

---

## 🧠 Simple Definition

> Strategy Pattern allows a class or application to change its behavior at runtime by injecting different algorithms (strategies).

---

## ❓ Why Do We Need It?

In real-world systems, we often have multiple ways to perform the same task:

- Different payment methods
- Different discount calculations
- Different sorting algorithms
- Different compression techniques

A naive implementation usually looks like this:

```java
if (type.equals("CREDIT_CARD")) {
    // credit card logic
} 
else if (type.equals("PAYPAL")) {
    // paypal logic
}
