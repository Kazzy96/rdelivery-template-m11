# 🤖 AI_FEATURE — OOP Extend BankAccount

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature modifies `BankAccount` directly — read [`oop-bank-account.feature.md`](oop-bank-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP Extend BankAccount
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Extend the existing `BankAccount` class to track a history of all transactions. Every deposit and withdrawal (whether successful or rejected) is recorded as a string entry in a list. The history can be retrieved via a dedicated method.

---

## Feature Scope

### In Scope (Included)

- Add a `transactions` field (`ArrayList<String>`) to `BankAccount`
- Initialise the list in the existing constructor
- Add `recordTransaction(String transaction)` method that appends a string to the list
- Add `getTransactionHistory()` method that returns the full list
- Update `deposit` to call `recordTransaction` after changing the balance
- Update `withdraw` to call `recordTransaction` for both successful and rejected cases
- `main` demo showing deposit, withdrawal, and printing the full transaction history

### Out of Scope (Excluded)

- No changes to `SavingsAccount`, `CheckingAccount`, or `BankCustomer`
- `generateReport()` is a separate feature (`oop-report.feature.md`)
- No timestamps, no amounts stored as numbers — transactions are plain strings

---

## Sub-Requirements (Feature Breakdown)

- **R1 — Add transactions list:** `ArrayList<String> transactions` field initialised to an empty list in the constructor
- **R2 — recordTransaction:** `recordTransaction(String transaction)` appends the string to `transactions`
- **R3 — getTransactionHistory:** `getTransactionHistory()` returns the `ArrayList<String>` of all recorded transactions
- **R4 — Update deposit:** After updating the balance, call `recordTransaction` with a descriptive string (e.g. `"Deposit: +$200.0 | Balance: $700.0"`)
- **R5 — Update withdraw:** Call `recordTransaction` for both the success case and the rejected case (e.g. `"Withdrawal denied: $700.0 — insufficient funds"`)
- **R6 — Main demo:** Create a `BankAccount`, perform deposits and withdrawals, then call `getTransactionHistory()` and print each entry

---

## User Flow / Logic (High Level)

1. A `BankAccount` is created — `transactions` list starts empty
2. `deposit(amount)` is called — balance updates and a transaction string is recorded
3. `withdraw(amount)` is called (valid) — balance updates and a transaction string is recorded
4. `withdraw(amount)` is called (invalid) — balance unchanged and a rejection string is recorded
5. `getTransactionHistory()` is called — returns all recorded strings in order
6. The `main` demo loops through the list and prints each transaction

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is the `main` method inside `BankAccount`.

---

## Data Used or Modified

- `transactions` — `ArrayList<String>`, grows with every deposit or withdraw call
- Each entry is a plain human-readable string describing what happened and the resulting balance

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- `recordTransaction` must be `protected` (or at minimum package-private) so subclasses can call it when they override `deposit`/`withdraw`
- `getTransactionHistory()` returns the list — do not print inside this method
- Do not break existing behaviour of `deposit` and `withdraw` — they must still print to console as before

---

## Acceptance Criteria

- [ ] `transactions` field exists and is initialised as an empty `ArrayList<String>` in the constructor
- [ ] `recordTransaction(String)` appends the string to the list
- [ ] `getTransactionHistory()` returns all recorded transactions in insertion order
- [ ] `deposit` records a transaction string after every call
- [ ] `withdraw` records a transaction string for both successful and rejected withdrawals
- [ ] `main` demo prints the full transaction history after performing operations
- [ ] Code compiles and runs without errors
- [ ] Subclasses (`SavingsAccount`, `CheckingAccount`) still compile and run correctly

---

## Notes for the AI

- Make `recordTransaction` `protected` so `SavingsAccount` and `CheckingAccount` can call it in their overridden methods if needed
- The transaction string format is flexible — keep it readable (e.g. include the amount and resulting balance)
- Subclasses already call `super` or manage `balance` directly — verify they will automatically benefit from the recorded transactions in the base class, or note that they will need updating in a later feature
