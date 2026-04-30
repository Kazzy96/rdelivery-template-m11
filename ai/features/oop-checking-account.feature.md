# 🤖 AI_FEATURE — OOP CheckingAccount

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on `BankAccount` — read [`oop-bank-account.feature.md`](oop-bank-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP CheckingAccount
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Extend `BankAccount` to create a `CheckingAccount` that adds an overdraft limit field and overrides `withdraw` to allow the balance to go negative — but only up to the overdraft limit. Any withdrawal that would exceed `balance + overdraftLimit` is rejected.

---

## Feature Scope

### In Scope (Included)

- `CheckingAccount` class that extends `BankAccount`
- `overdraftLimit` field (double) on `CheckingAccount`
- Constructor that accepts `accountNumber`, `balance`, and `overdraftLimit`
- `withdraw(double amount)` overridden to allow withdrawal when `amount ≤ balance + overdraftLimit`
- Rejection when `amount > balance + overdraftLimit`
- `main` method demonstrating a valid overdraft withdrawal and a rejected one

### Out of Scope (Excluded)

- No changes to `BankAccount` or `SavingsAccount`
- `deposit` is not overridden — no interest logic here
- The `oop-limits.feature.md` spec covers the stricter overdraft-denial rule as a later feature — do not implement it here separately
- No persistence, no Spring Boot, no database

---

## Sub-Requirements (Feature Breakdown)

- **R1 — Create class:** A new Java class named `CheckingAccount` in the `oop/` package
- **R2 — Extend BankAccount:** `CheckingAccount` must use `extends BankAccount`
- **R3 — Add field:** `overdraftLimit` (double) as an instance field on `CheckingAccount`
- **R4 — Constructor:** Accepts `accountNumber`, `balance`, and `overdraftLimit`; calls `super(accountNumber, balance)` and assigns `overdraftLimit`
- **R5 — Override withdraw:** `@Override withdraw(double amount)` allows withdrawal when `amount ≤ balance + overdraftLimit` (balance may go negative); rejects and prints a message when `amount > balance + overdraftLimit`
- **R6 — Main demo:** `main(String[] args)` creates one `CheckingAccount`, demonstrates a withdrawal that dips into the overdraft (succeeds), and a withdrawal that exceeds the overdraft limit (rejected)

---

## User Flow / Logic (High Level)

1. A `CheckingAccount` is created with an account number, an initial balance, and an overdraft limit (e.g. `$200`)
2. `withdraw(amount)` is called where `amount > balance` but `amount ≤ balance + overdraftLimit` — the withdrawal succeeds and balance goes negative
3. `withdraw(amount)` is called where `amount > balance + overdraftLimit` — the withdrawal is rejected and the balance stays unchanged
4. Balance is printed after each step to confirm correct behaviour

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is the `main` method inside `CheckingAccount`.

---

## Data Used or Modified

- `accountNumber` — inherited from `BankAccount`, set at construction
- `balance` — inherited from `BankAccount`; can go negative up to `-overdraftLimit`
- `overdraftLimit` — double, set at construction; defines how far below zero the balance is allowed to go

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Must use `extends BankAccount` and call `super(...)` in the constructor
- Must use `@Override` on the `withdraw` method
- Withdrawal condition: `amount <= balance + overdraftLimit` → allow; otherwise → reject
- Do not modify `BankAccount.java` or `SavingsAccount.java`

---

## Acceptance Criteria

- [ ] `CheckingAccount` class exists and extends `BankAccount`
- [ ] `overdraftLimit` field is present and initialised via constructor
- [ ] Constructor calls `super(accountNumber, balance)`
- [ ] `withdraw` is annotated with `@Override`
- [ ] Withdrawing $700 from a $500 balance with a $200 overdraft limit succeeds (balance → -$200)
- [ ] Withdrawing $701 from a $500 balance with a $200 overdraft limit is rejected (balance unchanged)
- [ ] `main` method compiles and runs without errors
- [ ] `main` demo output shows one successful overdraft withdrawal and one rejected withdrawal

---

## Notes for the AI

- The allow condition is `amount <= balance + overdraftLimit` — balance can go negative
- Print clearly when a withdrawal is rejected and why (e.g. `"exceeds overdraft limit"`)
- The `oop-limits.feature.md` spec may revisit this logic later — keep this implementation simple
- Keep the `main` demo short — two or three withdrawals are enough
