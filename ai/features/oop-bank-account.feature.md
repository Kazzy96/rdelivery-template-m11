# 🤖 AI_FEATURE — OOP BankAccount

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**

---

## Feature Identity

- **Feature Name:** OOP BankAccount
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Create a foundational `BankAccount` class that models a basic bank account in Java. The class stores an account number and a balance, allows deposits and withdrawals, and enforces a simple rule: you cannot withdraw more than the available balance.

---

## Feature Scope

### In Scope (Included)

- `BankAccount` class with two fields: `accountNumber` (String) and `balance` (double)
- Constructor that initializes both fields
- `deposit(double amount)` method that increases the balance
- `withdraw(double amount)` method that decreases the balance only if `amount ≤ balance`
- `main` method that creates a `BankAccount` instance and demonstrates a deposit followed by a withdrawal

### Out of Scope (Excluded)

- No inheritance — that belongs to `SavingsAccount` and `CheckingAccount` feature specs
- No transaction history — that is covered in the extend-bank-account feature spec
- No overdraft logic — that belongs to `CheckingAccount`
- No interest logic — that belongs to `SavingsAccount`
- No persistence, no database, no Spring Boot code

---

## Sub-Requirements (Feature Breakdown)

- **R1 — Create class:** A new Java class named `BankAccount` in the appropriate source directory
- **R2 — Add fields:** Two instance fields: `accountNumber` (String) and `balance` (double)
- **R3 — Constructor:** A constructor that accepts `accountNumber` and `balance` and assigns them to the fields
- **R4 — Deposit method:** `deposit(double amount)` — adds `amount` to `balance`
- **R5 — Withdraw method:** `withdraw(double amount)` — subtracts `amount` from `balance` only when `amount ≤ balance`; otherwise the balance is unchanged (no exception required, but a message may be printed)
- **R6 — Main demo:** A `main(String[] args)` method that creates one `BankAccount`, calls `deposit`, calls `withdraw` (at least one valid and one rejected case), and prints the balance at each step

---

## User Flow / Logic (High Level)

1. A `BankAccount` object is created with an account number and an initial balance
2. `deposit(amount)` is called — the balance increases by `amount`
3. `withdraw(amount)` is called with a valid amount — the balance decreases by `amount`
4. `withdraw(amount)` is called with an amount greater than the balance — the balance stays unchanged
5. The balance is printed after each operation to confirm correct behaviour

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable — this is a pure Java class exercise with no UI.

### Backend / API

Not applicable — this feature has no HTTP endpoints.

The entry point is the `main` method inside `BankAccount` (or a dedicated demo class).

---

## Data Used or Modified

- `accountNumber` — String, set at construction, never changed
- `balance` — double, starts at the constructor value, modified by `deposit` and `withdraw`

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Standard Java naming conventions: class in PascalCase, methods and fields in camelCase
- `withdraw` must enforce the `amount ≤ balance` rule — do not allow the balance to go negative in this class (overdraft is handled in `CheckingAccount`)
- Keep the class self-contained; do not couple it to any other class at this stage

---

## Acceptance Criteria

- [ ] `BankAccount` class exists with fields `accountNumber` (String) and `balance` (double)
- [ ] Constructor initialises both fields correctly
- [ ] `deposit(double amount)` increases the balance by the exact amount
- [ ] `withdraw(double amount)` decreases the balance when `amount ≤ balance`
- [ ] `withdraw(double amount)` leaves the balance unchanged when `amount > balance`
- [ ] `main` method compiles and runs without errors
- [ ] `main` demo prints the balance after each operation, showing both a successful and a rejected withdrawal

---

## Notes for the AI

- This class will be extended later by `SavingsAccount` and `CheckingAccount` — declare fields with `protected` (not `private`) so subclasses can access them directly, or provide `protected` getters/setters
- Keep the `main` demo short and readable — one or two deposits and one or two withdrawals are enough
- Do not add features not listed above; other sub-features (transaction history, reports, interest, overdraft limits) each have their own spec file
- Place the class in the `oop/` sub-package (or equivalent) so it is clearly separated from the Spring Boot application code
