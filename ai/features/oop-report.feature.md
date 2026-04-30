# 🤖 AI_FEATURE — OOP Report

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature modifies `BankCustomer`, `BankAccount`, `SavingsAccount`, and `CheckingAccount`.
> Read [`oop-bank-customer.feature.md`](oop-bank-customer.feature.md) and [`oop-extend-bank-account.feature.md`](oop-extend-bank-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP Report
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Add reporting capability to the banking class hierarchy. `BankCustomer` gains a `generateReport()` method that prints a summary of all accounts and their balances. Every account class gets a `toString()` override so each account can describe itself in a readable format.

---

## Feature Scope

### In Scope (Included)

- `toString()` overridden in `BankAccount`, `SavingsAccount`, and `CheckingAccount`
- `generateReport()` added to `BankCustomer` — prints customer name, each account via `toString()`, and total balance
- `main` demo in `BankCustomer` updated to call `generateReport()`

### Out of Scope (Excluded)

- No changes to `deposit`, `withdraw`, or transaction history logic
- No file output — report is printed to console only
- `updateInterestRate` belongs to `oop-interest.feature.md`
- Overdraft denial rule belongs to `oop-limits.feature.md`

---

## Sub-Requirements (Feature Breakdown)

- **R1 — toString in BankAccount:** Returns a string with the account number, type, and balance (e.g. `"[BankAccount] ACC-001 | Balance: $500.0"`)
- **R2 — toString in SavingsAccount:** Overrides `toString()` to include the interest rate (e.g. `"[SavingsAccount] SAV-001 | Balance: $1000.0 | Interest Rate: 5.0%"`)
- **R3 — toString in CheckingAccount:** Overrides `toString()` to include the overdraft limit (e.g. `"[CheckingAccount] CHK-001 | Balance: $500.0 | Overdraft Limit: $200.0"`)
- **R4 — generateReport in BankCustomer:** Prints the customer's name, iterates over all accounts calling `toString()` on each, and prints the total balance
- **R5 — Main demo:** `BankCustomer.main` calls `generateReport()` after setting up accounts

---

## User Flow / Logic (High Level)

1. A `BankCustomer` is created with multiple accounts of different types
2. `generateReport()` is called
3. It prints the customer's name, then each account's `toString()` output on its own line
4. It prints the total balance at the end

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is `BankCustomer.main`.

---

## Data Used or Modified

- Each account's `toString()` uses its own fields: `accountNumber`, `balance`, and type-specific fields (`interestRate`, `overdraftLimit`)
- `generateReport()` reads from the `accounts` collection already on `BankCustomer`

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- `toString()` must use `@Override`
- `generateReport()` must use `toString()` on each account (not manually print field by field)
- Do not break existing `main` demos in the individual account classes

---

## Acceptance Criteria

- [ ] `BankAccount.toString()` returns a readable string with account number and balance
- [ ] `SavingsAccount.toString()` returns a string that includes the interest rate
- [ ] `CheckingAccount.toString()` returns a string that includes the overdraft limit
- [ ] `BankCustomer.generateReport()` prints the customer name, all accounts via `toString()`, and total balance
- [ ] `BankCustomer.main` calls `generateReport()` and the output is correct
- [ ] All existing classes compile and run without errors
- [ ] Report output clearly shows each account type differently

---

## Notes for the AI

- Use `@Override` on all `toString()` methods
- `generateReport()` should use `System.out.println` — it does not need to return a String
- Keep `toString()` format consistent and readable — include the class name as a label (e.g. `[SavingsAccount]`)
