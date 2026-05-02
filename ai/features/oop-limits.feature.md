# 🤖 AI_FEATURE — OOP Limits

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature is fully satisfied by `CheckingAccount` — read [`oop-checking-account.feature.md`](oop-checking-account.feature.md) first.

---

## Feature Identity

- **Feature Name:** OOP Limits
- **Related Area:** Backend — Java OOP Exercise (Part 1)

---

## Feature Goal

Ensure `CheckingAccount.withdraw` enforces a hard limit on overdraft — any withdrawal where `amount > balance + overdraftLimit` must be denied and the balance must remain unchanged. A `main` demo must clearly show both a withdrawal that is allowed (even dipping into overdraft) and one that is rejected for exceeding the limit.

---

## Feature Scope

### In Scope (Included)

- `withdraw` override in `CheckingAccount` enforcing `amount ≤ balance + overdraftLimit`
- Rejection message when the limit is exceeded
- `main` demo showing:
  - A withdrawal within normal balance (no overdraft used)
  - A withdrawal that dips into overdraft but stays within the limit (allowed)
  - A withdrawal that exceeds `balance + overdraftLimit` (rejected)

### Out of Scope (Excluded)

- No changes to `BankAccount`, `SavingsAccount`, or `BankCustomer`
- No changes to `overdraftLimit` at runtime — that would be a separate feature
- No fee for using overdraft

---

## Sub-Requirements (Feature Breakdown)

- **R1 — withdraw override:** `CheckingAccount.withdraw` uses `@Override` and allows withdrawal only when `amount ≤ balance + overdraftLimit`
- **R2 — Denial when over limit:** When `amount > balance + overdraftLimit`, balance stays unchanged and a clear rejection message is printed
- **R3 — Main demo:** Three withdrawal calls demonstrating normal, overdraft-allowed, and overdraft-rejected outcomes

---

## User Flow / Logic (High Level)

1. A `CheckingAccount` is created (e.g. balance `$500`, overdraft limit `$200`)
2. A normal withdrawal is made (e.g. `$300`) — succeeds, balance → `$200`
3. An overdraft withdrawal is made (e.g. `$350`) — succeeds because `350 ≤ 200 + 200`, balance → `-$150`
4. Another withdrawal is attempted (e.g. `$100`) — rejected because `100 > -150 + 200` is false (`100 > 50` is true, so denied), balance stays at `-$150`
5. Output confirms the rejection

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable.

### Backend / API

Not applicable. Entry point is `CheckingAccount.main`.

---

## Data Used or Modified

- `balance` — decreases on allowed withdrawals; unchanged on rejected ones
- `overdraftLimit` — the ceiling; balance can go as low as `-overdraftLimit`

---

## Tech Constraints (Feature-Level)

- Plain Java only — no Spring Boot, no Lombok, no external libraries
- Denial condition: `amount > balance + overdraftLimit`
- `@Override` required on `withdraw`
- Do not modify any other class

---

## Acceptance Criteria

- [ ] `CheckingAccount.withdraw` is annotated with `@Override`
- [ ] Withdrawal is allowed when `amount ≤ balance + overdraftLimit` (balance may go negative)
- [ ] Withdrawal is denied when `amount > balance + overdraftLimit`
- [ ] Balance is unchanged after a denied withdrawal
- [ ] `main` demo includes all three cases: normal, overdraft-used, overdraft-exceeded
- [ ] Code compiles and runs without errors

---

## Implementation Status

> ✅ **Already implemented** in `CheckingAccount.java` as part of `oop-checking-account`.
> No additional code changes are required for this feature. The `main` demo in `CheckingAccount` already covers all three cases.

---

## Notes for the AI

- Do not re-implement or duplicate this logic — it is already present in `CheckingAccount.java`
- Verify the existing `main` demo covers all three cases before marking this complete
- The rejection case in the current demo: balance is `-$150`, overdraft limit is `$200`, so max allowable withdrawal is `$50`. Attempting `$100` correctly triggers the rejection
