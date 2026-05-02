# 🤖 AI_FEATURE — OOP BankCustomer

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on `BankAccount` — read [`oop-bank-account.feature.md`](oop-bank-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP BankCustomer
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Create a `BankCustomer` class that represents a bank customer who can hold multiple `BankAccount` objects. The class demonstrates **composition** — a customer *has* accounts rather than *being* an account. It provides a way to add accounts and compute the customer's total balance across all accounts.

---

## Feature Scope

### In Scope (Included)

- `BankCustomer` class (does not extend anything)
- `name` field (String)
- A collection of `BankAccount` objects (e.g. `ArrayList<BankAccount>`)
- Constructor that initialises `name` and an empty account collection
- `addAccount(BankAccount account)` method to add an account to the collection
- `totalBalance()` method that sums the balances of all accounts and returns the result
- `main` method that creates a customer, adds multiple accounts (mix of types), and prints the total balance

### Out of Scope (Excluded)

- `generateReport()` — belongs to `oop-report.feature.md`
- `toString()` overrides on account classes — belongs to `oop-report.feature.md`
- No `removeAccount` or account lookup methods
- No persistence, no Spring Boot, no database

---

## Sub-Requirements (Feature Breakdown)

- **R1 — Create class:** A new Java class named `BankCustomer` in the `oop/` package
- **R2 — Add fields:** `name` (String) and `accounts` (e.g. `ArrayList<BankAccount>`)
- **R3 — Constructor:** Accepts `name`; initialises `name` and creates an empty `ArrayList`
- **R4 — addAccount:** `addAccount(BankAccount account)` adds the given account to the collection
- **R5 — totalBalance:** `totalBalance()` iterates over all accounts, sums their balances, and returns the total as a `double`
- **R6 — Main demo:** Creates one `BankCustomer`, adds at least two different account types (e.g. a `BankAccount` and a `SavingsAccount`), prints each account's balance, and prints the total balance returned by `totalBalance()`

---

## User Flow / Logic (High Level)

1. A `BankCustomer` is created with a name
2. One or more `BankAccount` (or subclass) instances are created and added via `addAccount`
3. `totalBalance()` is called — it loops through all accounts and sums their balances
4. The total is printed to confirm it equals the sum of all individual balances

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is the `main` method inside `BankCustomer`.

---

## Data Used or Modified

- `name` — String, set at construction, identifies the customer
- `accounts` — `ArrayList<BankAccount>`, starts empty, grows via `addAccount`
- `totalBalance` — computed on demand by summing `getBalance()` on each account

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Use `ArrayList<BankAccount>` for the collection (not arrays)
- `totalBalance()` must return a `double`, not print it
- The `main` demo should include at least one `BankAccount` and one subclass instance to show polymorphism
- Do not modify `BankAccount`, `SavingsAccount`, or `CheckingAccount`

---

## Acceptance Criteria

- [ ] `BankCustomer` class exists in the `oop/` package
- [ ] `name` and `accounts` fields are present
- [ ] Constructor initialises `name` and creates an empty `ArrayList`
- [ ] `addAccount(BankAccount)` adds the account to the collection
- [ ] `totalBalance()` returns the correct sum of all account balances
- [ ] `main` demo creates a customer with at least two accounts and prints the total balance
- [ ] Total printed in `main` matches the sum of the individual account balances
- [ ] Code compiles and runs without errors

---

## Notes for the AI

- This class demonstrates **composition**, not inheritance — `BankCustomer` does not extend `BankAccount`
- `totalBalance()` should use `getBalance()` on each account, not access `balance` directly
- `generateReport()` and `toString()` overrides are intentionally excluded — they are part of `oop-report.feature.md`
- Keep the `main` demo readable — print each account balance before printing the total so it is easy to verify manually
