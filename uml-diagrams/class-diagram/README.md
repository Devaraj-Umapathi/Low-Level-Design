# UML Class Diagram Tutorial

## What is UML?

- UML (Unified Modeling Language) is a standardized graphical notation used for specifying, visualizing, constructing, and documenting the artifacts of a software system.
- It helps in understanding, documenting, designing, and maintaining complex software systems.

## Why use UML?

- Facilitates communication among stakeholders (developers, clients, etc.).
- Provides a clear, concise, and unambiguous representation of the system.
- Supports various stages of the software development lifecycle (analysis, design, implementation).
- Helps identify potential issues and inconsistencies early in the development process.

## UML Diagrams

UML diagrams are categorized into two main groups:

### 1. Structural Diagrams

- Class Diagram
- Object Diagram
- Component Diagram
- Deployment Diagram
- Package Diagram
- Profile Diagram
- Composite Structure Diagram

### 2. Behavioral Diagrams

- Use Case Diagram
- Activity Diagram
- State Machine Diagram
- Interaction Overview Diagram
- Sequence Diagram
- Communication Diagram
- Timing Diagram

## What is a Class Diagram?

- A static structural diagram that describes the structure of a system by showing the system's classes, their attributes, operations (or methods), and the relationships among classes.
- Provides a conceptual model of the system.
- Widely used in object-oriented modeling.

---

## Class Elements

### Class

Represents a blueprint for creating objects. It typically contains three compartments: Name, Attributes, and Operations.

**Diagram Example (Person Class):**

```
+-----------------+
|     Person      |
+-----------------+
| - name: String  |
| - age: int      |
+-----------------+
| + walk()        |
| + talk()        |
+-----------------+
```

### Attribute

A named property of a class that describes the range of values that instances of the property may hold. Visibility (private, public, protected, package) is indicated using symbols (`-` for private, `+` for public, `#` for protected, `~` for package).

**Diagram Example (BankAccount Class showing visibility):**

```
+-------------------+
|   BankAccount     |
+-------------------+
| - accountNumber: String |
| + balance: double |
| # owner: Person   |
+-------------------+
|                   |
+-------------------+
```

### Operation

A function or service that instances of a class can perform. It shows visibility, name, parameters, and return type.

**Diagram Example (Calculator Class):**

```
+---------------------------+
|        Calculator         |
+---------------------------+
|                           |
+---------------------------+
| + add(x: int, y: int): int |
| + subtract(x: int, y: int): int |
+---------------------------+
```

### Association

Represents a relationship between two or more classes. It can be bidirectional or unidirectional. Multiplicity (e.g., 1, *, 0..1, 1..*) is often shown at the ends of the association line.

**Diagram Example (Customer places Order):**

```
+----------+         +---------+
| Customer | --------> |  Order  |
+----------+  places   +---------+
|   1      |           |    *    |
+----------+         +---------+
```

*(An arrow from Customer to Order indicates the 'places' relationship. Multiplicity '1' is shown next to Customer, and '*' next to Order.)*

### Aggregation

A special type of association representing a "has-a" or "part-of" relationship where the part can exist independently of the whole. Represented by a hollow diamond on the whole side.

**Diagram Example (Department has Employees):**

```
+------------+ <>------ +----------+
| Department |          | Employee |
+------------+          +----------+
|     1      |          |    *     |
+------------+          +----------+
```

*(A hollow diamond is on the Department side, indicating Department is the whole and Employee is the part.)*

### Composition

A stronger form of aggregation where the part cannot exist independently of the whole. If the whole is destroyed, the parts are also destroyed. Represented by a filled diamond on the whole side.

**Diagram Example (Car has an Engine):**

```
+-----+ ◆---- +--------+
| Car |         | Engine |
+-----+         +--------+
|  1  |         |   1    |
+-----+         +--------+
```

*(A filled diamond is on the Car side. Multiplicity '1' for both.)*

### Generalization (Inheritance)

Represents an "is-a" relationship, where a subclass inherits properties and behaviors from a superclass.

**Diagram Example (Circle and Rectangle are Shapes):**

```
    +-------+
    | Shape |
    +-------+
       △
      /|\
     / | \
+-------+ +----------+
| Circle| | Rectangle|
+-------+ +----------+
```

*(An empty triangle arrow points from Circle and Rectangle to Shape, indicating inheritance.)*

### Realization (Interface Implementation)

Represents that a class implements the behavior specified by an interface.

**Diagram Example (Dog and Cat implement Animal interface):**

```
<<interface>>
+--------+
| Animal |
+--------+
   △
  /|\
+-----+  +-----+
| Dog |  | Cat |
+-----+  +-----+
```

*(A dashed line with an empty triangle arrow points from Dog and Cat to the Animal interface.)*

### Dependency

Indicates that one class depends on another. A change in one class might affect the other. Often represents temporary relationships (e.g., a method using another class as a parameter or local variable).

**Diagram Example (OrderProcessor depends on PaymentGateway):**

```
+---------------+ ---- - - -> +--------------+
| OrderProcessor|             | PaymentGateway |
+---------------+             +--------------+
```

*(A dashed arrow from OrderProcessor to PaymentGateway.)*

---

## Video explanation

[UML Class Diagram Explained](https://youtu.be/7HS6t_eMVJM?si=Gr2ymMqUlr7xLOmA) — video walkthrough of this content.
