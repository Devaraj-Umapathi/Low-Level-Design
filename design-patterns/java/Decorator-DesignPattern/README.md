# ☕ Decorator Design Pattern - Java Example

## 🧠 What is the Decorator Pattern?

The **Decorator Pattern** is a **structural design pattern** used to **dynamically add behavior or responsibilities** to individual objects, without modifying their code. It promotes **flexibility and reusability** over subclassing.

It follows the **Open/Closed Principle** — classes should be **open for extension, but closed for modification**.

---

## 📌 Use Case

Imagine a coffee shop where customers can customize their coffee by adding extras like milk, sugar, or cream.

Using subclassing, we’d have to create a new class for every combination of extras. This leads to **class explosion**:

```
SimpleCoffee
MilkCoffee
SugarCoffee
MilkSugarCoffee
CreamCoffee
...
```

Using the **Decorator Pattern**, we can wrap a base coffee object with as many decorators (add-ons) as needed.

---

## 🧱 Pattern Structure

```
Coffee (interface)
   ↑
SimpleCoffee (concrete class)
   ↑
CoffeeDecorator (abstract class)
   ↑
MilkDecorator, SugarDecorator, CreamDecorator (concrete decorators)
```

Each decorator wraps another `Coffee` object and adds its own behavior.

---

## 💡 Components

- **Component (`Coffee`)**: The common interface for all coffee types.
- **Concrete Component (`SimpleCoffee`)**: The base implementation.
- **Decorator (`CoffeeDecorator`)**: An abstract class that implements `Coffee` and wraps another `Coffee` object.
- **Concrete Decorators (`MilkDecorator`, `SugarDecorator`, etc.)**: Add new behavior.

---

## ✅ Benefits

- Adds behavior dynamically at runtime.
- Avoids subclass explosion.
- Promotes code reusability and flexibility.
- Follows the Open/Closed and Single Responsibility Principles.

---

## 🧩 When to Use

- When you need to **add responsibilities** to objects without altering their class.
- When subclassing would lead to an **explosion of subclasses**.
- When you need a **flexible alternative to subclassing** for extending functionality.

---