# 🤖 AI_FEATURE — All Services

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on all repositories being complete — read [`all-repositories.feature.md`](all-repositories.feature.md) first.

---

## Feature Identity

- **Feature Name:** All Services
- **Related Area:** Backend — Part 2, Spring Boot Server

---

## Feature Goal

Create a `@Service` class for each of the 9 entities. Each service is the sole holder of business logic and orchestrates its corresponding repository. Controllers must never call repositories directly; they go through the service. Services handle missing/invalid data by returning `Optional` from find-by-ID and letting the controller decide how to respond.

---

## Feature Scope

### In Scope (Included)

- 9 service classes: `UserService`, `AddressService`, `EmployeeService`, `RestaurantService`, `CustomerService`, `ProductService`, `OrderStatusService`, `OrderService`, `ProductOrderService`
- Package: `com.rocketFoodDelivery.rocketFood.service`
- `@Service` annotation on each class
- Constructor-based dependency injection (no `@Autowired` field injection)
- Four standard CRUD methods per service:
  - `findAll()` — returns `List<Entity>` ordered by ID descending
  - `findById(int id)` — returns `Optional<Entity>`
  - `save(Entity entity)` — persists and returns the saved `Entity`
  - `deleteById(int id)` — removes by ID (`@Transactional`)

### Out of Scope (Excluded)

- No HTTP handling — that belongs in controllers
- No `@Query` annotations — standard repository methods are sufficient
- No pagination beyond the ordered list
- No authentication or authorization logic

---

## Sub-Requirements (Feature Breakdown)

| Service | Repository Injected | Entity |
|---|---|---|
| `UserService` | `UserRepository` | `User` |
| `AddressService` | `AddressRepository` | `Address` |
| `EmployeeService` | `EmployeeRepository` | `Employee` |
| `RestaurantService` | `RestaurantRepository` | `Restaurant` |
| `CustomerService` | `CustomerRepository` | `Customer` |
| `ProductService` | `ProductRepository` | `Product` |
| `OrderStatusService` | `OrderStatusRepository` | `OrderStatus` |
| `OrderService` | `OrderRepository` | `Order` |
| `ProductOrderService` | `ProductOrderRepository` | `ProductOrder` |

---

## User Flow / Logic (High Level)

Not user-facing — called by controllers only.

1. Controller calls `service.findAll()` → service delegates to `repository.findAllByOrderByIdDesc()` → controller adds result to model → view renders list
2. Controller calls `service.findById(id)` → service returns `Optional<Entity>` → controller checks `isPresent()` before binding to form, returns 404 if empty
3. Controller calls `service.save(entity)` on POST → service calls `repository.save(entity)` → controller redirects to list
4. Controller calls `service.deleteById(id)` on delete action → service delegates to `repository.deleteById(id)` → controller redirects to list

---

## Interfaces (Pages, Endpoints, Screens)

### Frontend

Not applicable at this layer.

### Backend / API

Services are internal — no HTTP routes defined here. See `all-controllers.feature.md`.

---

## Data Used or Modified

Each service reads from and writes to its entity table via its repository. No cross-entity business logic is required at this stage.

---

## Validation & Error Handling

- `findById` returns `Optional<Entity>` — never `null`. The controller handles the empty case (404 / redirect).
- `save` relies on Jakarta Bean Validation constraints already defined on entity fields — no duplicate validation in the service.
- `deleteById` is annotated `@Transactional` to ensure atomicity.
- No try/catch blocks — exceptions propagate to Spring's default error handling.

---

## Tech Constraints (Feature-Level)

- Package: `com.rocketFoodDelivery.rocketFood.service`
- Every service must be a `class` (not an interface) annotated `@Service`
- Constructor injection only — no `@Autowired` on fields
- `findAll()` must call `findAllByOrderByIdDesc()` (not plain `findAll()`) so list pages show newest first
- `findById` must return `Optional<Entity>` with the exact entity generic type
- `deleteById` must be annotated `@Transactional`

---

## Acceptance Criteria

- [ ] All 9 service classes exist in the `service` package
- [ ] Each is annotated with `@Service`
- [ ] Each uses constructor injection for its repository
- [ ] Each exposes `findAll()`, `findById(int)`, `save(Entity)`, `deleteById(int)`
- [ ] `findAll()` delegates to `findAllByOrderByIdDesc()`
- [ ] `findById` returns `Optional<Entity>`
- [ ] `deleteById` is annotated `@Transactional`
- [ ] Spring Boot starts without errors after all services are in place
- [ ] No compilation errors in any service class

---

## Notes for the AI

- `UserService`, `AddressService`, and `CustomerService` already exist with partial implementations — complete them, do not overwrite structure
- `EmployeeService`, `RestaurantService`, `ProductService`, `OrderService`, `OrderStatusService`, `ProductOrderService` are empty stubs — implement from scratch
- Do not import `@Autowired` — Spring auto-detects constructor injection
- `Optional` is from `java.util.Optional`
- `@Transactional` is from `org.springframework.transaction.annotation.Transactional`
