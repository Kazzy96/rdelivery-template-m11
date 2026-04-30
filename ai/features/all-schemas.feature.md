# 🤖 AI_FEATURE — All Schemas

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> The ERD in `assets/support_materials_11v2/db_schema_11.txt` is the source of truth — every field, type, and relationship must match exactly.

---

## Feature Identity

- **Feature Name:** All Schemas
- **Related Area:** Backend — Part 2, Spring Boot Server

---

## Feature Goal

Create all 9 JPA entity classes that exactly match the provided ERD. Each entity maps to a MySQL table, includes proper Lombok, JPA, and Jakarta validation annotations, and correctly defines relationships between tables.

---

## Feature Scope

### In Scope (Included)

- 9 entity classes: `User`, `Address`, `Employee`, `Restaurant`, `Customer`, `Product`, `OrderStatus`, `Order`, `ProductOrder`
- Correct package: `com.rocketFoodDelivery.rocketFood.models`
- Lombok annotations: `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
- JPA annotations: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`, `@JoinColumn`, `@ManyToOne`, `@OneToOne`
- Jakarta validation: `@NotBlank`, `@Email`, `@Min`, `@Max`, `@Pattern`
- Hibernate timestamps: `@CreationTimestamp`, `@UpdateTimestamp`
- Relationships matching the ERD exactly

### Out of Scope (Excluded)

- No repositories, services, or controllers — those are separate features
- No ERD changes — follow the schema exactly
- No extra fields not in the ERD

---

## Sub-Requirements (Feature Breakdown)

### TABLE: users → `User.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| name | String | not null — `@NotBlank`, `@Column(nullable = false)` |
| email | String | not null, unique — `@NotBlank`, `@Column(nullable = false, unique = true)` |
| password | String | not null — `@NotBlank`, `@Column(nullable = false)` |

---

### TABLE: addresses → `Address.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| streetAddress | String | not null — `@NotBlank`, `@Column(nullable = false)` |
| city | String | not null — `@NotBlank`, `@Column(nullable = false)` |
| postalCode | String | not null — `@NotBlank`, `@Column(nullable = false)` |

---

### TABLE: employees → `Employee.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| user | User | `@OneToOne`, `@JoinColumn(name = "user_id", nullable = false, unique = true)` |
| address | Address | `@ManyToOne`, `@JoinColumn(name = "address_id", nullable = false)` |
| phone | String | not null — `@Column(nullable = false)` |
| email | String | optional — `@Email` only |

---

### TABLE: restaurants → `Restaurant.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| user | User | `@ManyToOne`, `@JoinColumn(name = "user_id", nullable = false)` |
| address | Address | `@OneToOne`, `@JoinColumn(name = "address_id", nullable = false, unique = true)` |
| phone | String | not null — `@Column(nullable = false)` |
| email | String | optional — `@Email` only |
| name | String | not null — `@Column(nullable = false)` |
| priceRange | int | not null, 1–3, default 1 — `@Min(1)`, `@Max(3)`, `@Column(nullable = false, columnDefinition = "integer default 1")` |
| active | boolean | not null, default true — `@Column(nullable = false, columnDefinition = "boolean default true")` |

---

### TABLE: customers → `Customer.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| user | User | `@OneToOne`, `@JoinColumn(name = "user_id", nullable = false, unique = true)` |
| address | Address | `@ManyToOne`, `@JoinColumn(name = "address_id", nullable = false)` |
| phone | String | not null — `@Column(nullable = false)` |
| email | String | optional — `@Email` only |
| active | boolean | not null, default true — `@Column(nullable = false, columnDefinition = "boolean default true")` |

---

### TABLE: products → `Product.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| restaurant | Restaurant | `@ManyToOne`, `@JoinColumn(name = "restaurant_id", nullable = false)` |
| name | String | not null — `@Column(nullable = false)` |
| description | String | optional |
| cost | int | not null, min 0 — `@Min(0)`, `@Column(nullable = false)` |

---

### TABLE: order_statuses → `OrderStatus.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| name | String | not null, allowed values only — `@NotBlank`, `@Pattern(regexp = "pending\|in progress\|delivered")`, `@Column(nullable = false)` |

---

### TABLE: orders → `Order.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| restaurant | Restaurant | `@ManyToOne`, `@JoinColumn(name = "restaurant_id", nullable = false)` |
| customer | Customer | `@ManyToOne`, `@JoinColumn(name = "customer_id", nullable = false)` |
| orderStatus | OrderStatus | `@ManyToOne`, `@JoinColumn(name = "order_status_id", nullable = false)` |
| restaurantRating | Integer | optional, 1–5 — `@Min(1)`, `@Max(5)` |

---

### TABLE: product_orders → `ProductOrder.java`
| Field | Type | Constraints |
|---|---|---|
| id | int | PK, auto-increment |
| product | Product | `@ManyToOne`, `@JoinColumn(name = "product_id", nullable = false)` |
| order | Order | `@ManyToOne`, `@JoinColumn(name = "order_id", nullable = false)` |
| productQuantity | int | not null, min 1 — `@Min(1)`, `@Column(name = "product_quantity", nullable = false)` |
| productUnitCost | int | not null, min 0 — `@Min(0)`, `@Column(name = "product_unit_cost", nullable = false)` |

**Business rule:** No two `ProductOrder` rows may share the same `product_id` + `order_id` combination → `@UniqueConstraint(columnNames = {"product_id", "order_id"})` on `@Table`.

---

## User Flow / Logic (High Level)

Not applicable — entities are passive data containers. They are used by repositories, services, and controllers in later features.

---

## Interfaces (Pages, Endpoints, Screens)

Not applicable at this layer. Entities are accessed through repositories defined in `all-repositories.feature.md`.

---

## Tech Constraints (Feature-Level)

- Package: `com.rocketFoodDelivery.rocketFood.models`
- Use `@NotBlank` (not `@NotNull`) for required String fields — `@NotNull` allows empty strings
- Use `@Column(nullable = false)` alongside validation annotations — two layers of protection
- Use `columnDefinition` for database-level defaults (`boolean default true`, `integer default 1`)
- Do not use `@NotNull` on String fields — use `@NotBlank`
- Follow the ERD relationship types exactly (`@OneToOne` vs `@ManyToOne`)

---

## Acceptance Criteria

- [ ] All 9 entity classes exist in the `models` package
- [ ] Every class has `@Entity`, `@Table(name = ...)`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
- [ ] Every class has `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` on the `id` field
- [ ] All required String fields use `@NotBlank` and `@Column(nullable = false)`
- [ ] All optional String fields use `@Email` only (no `nullable = false`)
- [ ] All relationships match the ERD (`@ManyToOne` / `@OneToOne` with correct `@JoinColumn`)
- [ ] `priceRange` and `active` on `Restaurant` have correct `columnDefinition` for DB defaults
- [ ] `active` on `Customer` has `@Column(nullable = false, columnDefinition = "boolean default true")`
- [ ] `ProductOrder` has a unique constraint on `(product_id, order_id)`
- [ ] Spring Boot starts without schema errors

---

## Notes for the AI

- `Address` fields (`streetAddress`, `city`, `postalCode`) map to snake_case columns via Hibernate's naming strategy — explicit `@Column(name = ...)` is not required unless the name differs
- `@NotNull` on a String field allows `""` (empty string) to pass validation — always use `@NotBlank` for required strings
- Check existing files before writing — several entities already exist and only need corrections
