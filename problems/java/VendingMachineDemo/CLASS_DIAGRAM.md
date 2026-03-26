# Vending Machine — Class Diagram

## Mermaid (paste into GitHub, Notion, VS Code preview, etc.)

```mermaid
classDiagram
    direction TB

    class VendingMachine {
        -Inventory inventory
        -State currentState
        -Double currentAmount
        -IdleState idleState
        -MoneyInsertedState moneyInsertedState
        -DispenseState dispenseState
        +getInstance() VendingMachine
        +loadProduct(rack, product, qty)
        +reStock(rack, qty)
        +insertMoney(amount)
        +selectProduct(rackNumber)
        +refund()
        +beginDispensing(rack)
        +reset()
        +addAmount(amount)
        +setState(state)
        +getInventory()
        +getIdleState()
        +getMoneyInsertedState()
        +getDispenseState()
        +getCurrentAmount()
    }

    class Inventory {
        -Map racks
        +Inventory(rackCount, maxUnitsPerRack)
        +getRack(int) Rack
        +getRackCount() int
    }

    class Rack {
        -Integer rackNumber
        -int maxCapacity
        -Product product
        -Integer quantity
        +loadProduct(product, qty)
        +reStock(qty) boolean
        +dispenseOne()
        +getMaxCapacity() int
        +getRackNumber() Integer
        +getQuantity() Integer
        +getProduct() Product
    }

    class Product {
        -int productId
        -String name
        -double price
        -ProductType productType
        +getProductId() int
        +getName() String
        +getPrice() double
        +getProductType() ProductType
    }

    class ProductType {
        <<enumeration>>
        CHOCOLATE
        CHIPS
        BEVERAGE
        BISCUITS
        OTHERS
    }

    class State {
        <<interface>>
        insertMoney(vm, amount)
        selectProduct(vm, rackNumber)
    }

    class IdleState {
        +insertMoney(vm, amount)
        +selectProduct(vm, rackNumber)
    }

    class MoneyInsertedState {
        +insertMoney(vm, amount)
        +selectProduct(vm, rackNumber)
    }

    class DispenseState {
        +insertMoney(vm, amount)
        +selectProduct(vm, rackNumber)
        +dispense(vm, rack)
    }

    class VendingMachineDemo {
        +main(args)
    }

    VendingMachine *-- Inventory : owns
    VendingMachine o-- State : currentState
    VendingMachine *-- IdleState : owns
    VendingMachine *-- MoneyInsertedState : owns
    VendingMachine *-- DispenseState : owns

    Inventory "1" *-- "1..*" Rack : Map values

    Rack --> "0..1" Product : stocked SKU

    Product --> ProductType

    State <|.. IdleState : implements
    State <|.. MoneyInsertedState : implements
    State <|.. DispenseState : implements

    VendingMachineDemo ..> VendingMachine : uses
    VendingMachineDemo ..> Product : constructs

    IdleState ..> VendingMachine : invokes
    MoneyInsertedState ..> VendingMachine : invokes
    DispenseState ..> VendingMachine : invokes
```

---

## Legend

| Symbol / relation | Meaning |
|-------------------|--------|
| `*--` | **Composition** — lifecycle owned by parent (`VendingMachine` owns `Inventory` and concrete states). |
| `o--` | **Aggregation / association** — `currentState` references the active `State` implementation. |
| `*--` Inventory–Rack | `Inventory` creates and holds all `Rack` instances in a `Map<Integer, Rack>`. |
| `-->` Rack–Product | Each rack points to **at most one** `Product` (the SKU in that slot). |
| `..>` | **Dependency** — demo and states call methods on `VendingMachine` (and `Product` in demo). |
| `State` implementors | **State pattern** — behaviour of `insertMoney` / `selectProduct` depends on `currentState`. |

---

## Sequence note (not shown on class diagram)

`MoneyInsertedState.selectProduct` may call `setState(dispenseState)` and then `beginDispensing(rack)`, which delegates to `DispenseState.dispense(...)`. Exact ordering matches your `VendingMachine` / state code.

---

## Export as image

- **Mermaid Live Editor:** [mermaid.live](https://mermaid.live) — paste the diagram block, export PNG/SVG.  
- **IDE:** Markdown preview with Mermaid support often has export.  
- **PlantUML:** If you need `.puml`, the same structure maps one-to-one to `class` blocks and relations.
