# 🏗️ Builder Design Pattern

The **Builder Pattern** is a creational design pattern that allows you to construct complex objects step-by-step. It separates the construction of an object from its representation, so the same construction process can create different representations.

---

## 📖 Summary

- **Problem:** Constructors with many parameters (some optional, some required) are hard to read, write, and maintain.
- **Solution:** Use a builder object to set required and optional fields step-by-step, then call `build()` to create the final object.

---

## 📦 Structure

- **Product** – The object being built (e.g., `Computer`)
- **Builder** – Interface or class that defines how to build the product
- **ConcreteBuilder** – Implements the builder methods and returns the final object
- **Director (optional)** – Controls the building process for different variations (e.g., `GamingComputer` vs `OfficeComputer`)

---
