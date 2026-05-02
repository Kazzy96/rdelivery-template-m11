# 🤖 AI_FEATURE — OOP Interest

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature modifies `SavingsAccount` — read [`oop-savings-account.feature.md`](oop-savings-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP Interest
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Allow the interest rate on a `SavingsAccount` to be changed after the account is created. The updated rate must be reflected immediately on the next deposit.

---

## Feature Scope

### In Scope (Included)

- `updateInterestRate(double newRate)` method added to `SavingsAccount`
- Updated `main` demo showing: initial deposit at old rate → rate change → deposit at new rate

### Out of Scope (Excluded)

- No changes to `BankAccount`, `CheckingAccount`, or `BankCustomer`
- No validation of the new rate value (e.g. negative rates) — keep it simple
- No recalculation of past deposits — only future deposits use the new rate

---

## Sub-Requirements (Feature Breakdown)

- **R1 — updateInterestRate:** `updateInterestRate(double newRate)` sets `interestRate` to `newRate` and prints a confirmation message
- **R2 — Main demo:** Create a `SavingsAccount`, deposit at the original rate, call `updateInterestRate`, then deposit again to show the new rate in effect

---

## User Flow / Logic (High Level)

1. A `SavingsAccount` is created with an initial interest rate (e.g. 5%)
2. A deposit is made — interest calculated at 5%
3. `updateInterestRate(0.10)` is called — rate changes to 10%
4. Another deposit is made — interest now calculated at 10%
5. Output confirms the rate changed and the new deposit reflects it

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is `SavingsAccount.main`.

---

## Data Used or Modified

- `interestRate` — updated by `updateInterestRate`; immediately used on next `deposit` call

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Do not modify `BankAccount`, `CheckingAccount`, or `BankCustomer`
- The existing `deposit` override already uses `interestRate` — no changes needed to `deposit` itself

---

## Acceptance Criteria

- [ ] `updateInterestRate(double newRate)` exists on `SavingsAccount`
- [ ] Calling it changes the rate used by subsequent deposits
- [ ] `main` demo shows a deposit before and after the rate change, with different interest amounts
- [ ] Code compiles and runs without errors

---

## Notes for the AI

- `deposit` already reads from `interestRate` — simply updating the field is enough
- Print a clear message in `updateInterestRate` so the demo output shows the change (e.g. `"Interest rate updated to 10.0%"`)
- Keep the demo short — one deposit before the update and one after is sufficient
