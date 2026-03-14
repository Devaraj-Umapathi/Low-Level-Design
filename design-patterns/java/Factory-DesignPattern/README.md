# 🍕 Factory Design Pattern - Food App Example

Factory Design Pattern is a creational design pattern that provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.

- It abstracts the object creation process.
- Helps in creating objects without exposing the instantiation logic to the client.
- The client only interacts with the common interface, not the concrete classes.

---

## 🔧 Problem

You're building a food delivery app where users can order different dishes like Pizza, Burger, or Pasta. Instead of creating food items directly using `new`, we delegate the creation logic to factories.

---

## ✅ Benefits
- Follows Open/Closed Principle (OCP)
- Removes object creation logic from the client
- Easier to extend and test

---

## ☑️ Components of Factory Method Design Pattern
- Product: Represents the interface of the objects created by the factory method.
- ConcreteProduct: Implements the WithFactoryPattern.Product interface and represents the concrete objects created by the factory method.
- Creator: Declares the factory method, which returns an instance of the WithFactoryPattern.Product interface.
- ConcreteCreator: Implements the factory method to create instances of WithFactoryPattern.ConcreteProduct.

---

## 🏗️ Structure

- **Product**: `Food`
- **Concrete Products**: `Pizza`, `Burger`, `Pasta`
- **Creator Interface**: `FoodFactory`
- **Concrete Creators**: `PizzaFactory`, `BurgerFactory`, `PastaFactory`
- **Client**: `FoodApp` (main app)

---