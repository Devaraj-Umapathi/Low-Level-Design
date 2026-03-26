# Vending Machine Demo (Python)

Python version of the Java `VendingMachineDemo` using the same LLD structure and behavior.

## Run

From this folder:

```bash
PYTHONPATH=src python3 src/vending_machine_demo.py
```

## Structure

- `src/enums/product_type.py`
- `src/models/{product, rack, inventory}.py`
- `src/states/{state, idle_state, money_inserted_state, dispense_state}.py`
- `src/services/vending_machine.py`
- `src/vending_machine_demo.py`
