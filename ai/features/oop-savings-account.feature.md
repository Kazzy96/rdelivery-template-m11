# 🤖 AI_FEATURE — OOP SavingsAccount

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on `BankAccount` — read [`oop-bank-account.feature.md`](oop-bank-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP SavingsAccount
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Extend `BankAccount` to create a `SavingsAccount` that adds an interest rate field and overrides the `deposit` method so every deposit automatically adds interest on top of the deposited amount.

---

## Feature Scope

### In Scope (Included)

- `SavingsAccount` class that extends `BankAccount`
- `interestRate` field (double) added to `SavingsAccount`
- Constructor that accepts `accountNumber`, `balance`, and `interestRate`
- `deposit(double amount)` overridden so that the amount deposited is `amount + (amount * interestRate)`
- `main` method that creates a `SavingsAccount` and demonstrates a deposit with interest applied

### Out of Scope (Excluded)

- No changes to `BankAccount` — it must remain unmodified
- `withdraw` is not overridden in this feature (overdraft belongs to `CheckingAccount`)
- `updateInterestRate` is a separate feature (`oop-interest.feature.md`) — do not implement it here
- No persistence, no Spring Boot, no database

---

## Sub-Requirements (Feature Breakdown)

- **R1 — Create class:** A new Java class named `SavingsAccount` in the same `oop/` package as `BankAccount`
- **R2 — Extend BankAccount:** `SavingsAccount` must use `extends BankAccount`
- **R3 — Add field:** `interestRate` (double) as an instance field on `SavingsAccount`
- **R4 — Constructor:** Accepts `accountNumber`, `balance`, and `interestRate`; calls `super(accountNumber, balance)` and assigns `interestRate`
- **R5 — Override deposit:** `@Override deposit(double amount)` deposits `amount + (amount * interestRate)` into the balance and prints the deposit amount, interest earned, and new balance
- **R6 — Main demo:** `main(String[] args)` creates one `SavingsAccount`, prints the opening balance, calls `deposit` at least once, and confirms interest was added

---

## User Flow / Logic (High Level)

1. A `SavingsAccount` is created with an account number, an initial balance, and an interest rate (e.g. `0.05` for 5%)
2. `deposit(amount)` is called — the actual amount added to the balance is `amount + (amount * interestRate)`
3. The new balance is printed, clearly showing more than the raw deposit was added
4. `withdraw` behaves exactly as in `BankAccount` (inherited, not overridden)

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is the `main` method inside `SavingsAccount`.

---

## Data Used or Modified

- `accountNumber` — inherited from `BankAccount`, set at construction
- `balance` — inherited from `BankAccount`, increased by deposit + interest
- `interestRate` — double, set at construction, used in deposit calculation

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Must use `extends BankAccount` and call `super(...)` in the constructor
- Must use `@Override` on the `deposit` method
- Do not modify `BankAccount.java`

---

## Acceptance Criteria

- [ ] `SavingsAccount` class exists and extends `BankAccount`
- [ ] `interestRate` field is present and initialised via constructor
- [ ] Constructor calls `super(accountNumber, balance)`
- [ ] `deposit` is annotated with `@Override`
- [ ] Depositing $100 at 5% interest results in $105 added to the balance
- [ ] `main` method compiles and runs without errors
- [ ] `main` demo output clearly shows that the balance increased by more than the raw deposit amount

---

## Notes for the AI

- The interest formula is: `effectiveDeposit = amount + (amount * interestRate)`
- Print the interest earned separately so the demo output is clear (e.g. `Interest earned: $5.0`)
- Keep the `main` demo short — one or two deposits are enough
- Do not implement `updateInterestRate` here; that belongs to a later feature spec
