# 🤖 AI_FEATURE — All Repositories

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on all entities being complete — read [`all-schemas.feature.md`](all-schemas.feature.md) first.

---

## Feature Identity

- **Feature Name:** All Repositories
- **Related Area:** Backend — Part 2, Spring Boot Server

---

## Feature Goal

Create a Spring Data JPA repository interface for each of the 9 entities. Each repository extends `JpaRepository`, giving the service layer full CRUD access with zero boilerplate. A consistent custom query method (`findAllByOrderByIdDesc`) is added to every repository so list pages display newest records first.

---

## Feature Scope

### In Scope (Included)

- 9 repository interfaces: `UserRepository`, `AddressRepository`, `EmployeeRepository`, `RestaurantRepository`, `CustomerRepository`, `ProductRepository`, `OrderStatusRepository`, `OrderRepository`, `ProductOrderRepository`
- Correct package: `com.rocketFoodDelivery.rocketFood.repository`
- `@Repository` annotation on each interface
- `extends JpaRepository<Entity, Integer>` on each
- `findAllByOrderByIdDesc()` custom query method on each — returns `List<Entity>`

### Out of Scope (Excluded)

- No service or controller logic — those are separate features
- No complex `@Query` annotations unless Spring Data naming conventions cannot express the query
- No pagination or sorting parameters beyond the standard ordering method

---

## Sub-Requirements (Feature Breakdown)

| Repository | Entity | Custom Methods |
|---|---|---|
| `UserRepository` | `User` | `findAllByOrderByIdDesc()` |
| `AddressRepository` | `Address` | `findAllByOrderByIdDesc()` |
| `EmployeeRepository` | `Employee` | `findAllByOrderByIdDesc()` |
| `RestaurantRepository` | `Restaurant` | `findAllByOrderByIdDesc()` |
| `CustomerRepository` | `Customer` | `findAllByOrderByIdDesc()` |
| `ProductRepository` | `Product` | `findAllByOrderByIdDesc()` |
| `OrderStatusRepository` | `OrderStatus` | `findAllByOrderByIdDesc()` |
| `OrderRepository` | `Order` | `findAllByOrderByIdDesc()` |
| `ProductOrderRepository` | `ProductOrder` | `findAllByOrderByIdDesc()` |

---

## User Flow / Logic (High Level)

Not applicable — repositories are accessed by services, not directly by users.

1. Service calls `repository.findAllByOrderByIdDesc()` → returns all records newest-first
2. Service calls `repository.findById(id)` → returns an `Optional<Entity>`
3. Service calls `repository.save(entity)` → inserts or updates the record
4. Service calls `repository.deleteById(id)` → removes the record

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable at this layer.

### Backend / API

Not applicable directly — accessed through the service layer defined in `all-services.feature.md`.

---

## Data Used or Modified

Each repository reads from and writes to its corresponding MySQL table via JPA/Hibernate. No business logic lives here.

---

## Tech Constraints (Feature-Level)

- Package: `com.rocketFoodDelivery.rocketFood.repository`
- Every repository must be an `interface` (not a class)
- Must have `@Repository` annotation
- Must extend `JpaRepository<EntityClass, Integer>`
- Do not write method implementations — Spring Data generates them at runtime
- `findAllByOrderByIdDesc()` must return `List<EntityClass>`

---

## Acceptance Criteria

- [ ] All 9 repository interfaces exist in the `repository` package
- [ ] Each is annotated with `@Repository`
- [ ] Each extends `JpaRepository<Entity, Integer>`
- [ ] Each has `findAllByOrderByIdDesc()` returning `List<Entity>`
- [ ] Spring Boot starts without errors after all repositories are in place
- [ ] The app can be started and data seeder runs without repository-related errors

---

## Notes for the AI

- `UserRepository` and `AddressRepository` already exist and are correct — do not overwrite them
- `CustomerRepository` already exists and is correct — do not overwrite it
- The 6 remaining stubs (`Employee`, `Restaurant`, `Product`, `OrderStatus`, `Order`, `ProductOrder`) need to be implemented
- Follow the exact same pattern as the existing `AddressRepository`
