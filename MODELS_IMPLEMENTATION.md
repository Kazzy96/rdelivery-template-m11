# Entity Models Implementation — Rocket Food Delivery

This document explains every change made to the `models/` directory to match the ERD schema.

---

## How It All Works

Think of the database as a filing cabinet for a food delivery company — one folder (table) for customers, one for restaurants, one for orders, and so on. The Java model files are the **forms** that define what information goes into each folder and what rules it must follow.

Spring Boot uses a library called **JPA** to automatically translate those Java forms into real database tables. You write a Java class, add some annotations (special labels starting with `@`), and JPA handles creating the matching table in MySQL. Three sets of annotations are used throughout:

- **Lombok** (`@Data`, `@Builder`, etc.) — auto-generates repetitive code like getters, setters, and constructors so you don't have to write them manually.
- **JPA** (`@Entity`, `@ManyToOne`, `@JoinColumn`, etc.) — tells Spring how each Java class maps to a database table and how tables relate to each other.
- **Jakarta Validation** (`@NotBlank`, `@Min`, `@Email`, etc.) — enforces data rules so bad data gets rejected before it ever reaches the database.

---

## 1. `User.java` — Fixed

The `User` form already existed but was missing enforcement on its most important fields. `name`, `email`, and `password` were present but had no `@Column(nullable = false)` annotation, meaning the database would happily accept a user with no name or password. `@NotBlank` was also missing from `name` and `password`, so validation wouldn't catch empty strings either.

**Fix:** Added `@NotBlank` and `@Column(nullable = false)` to all three fields. This enforces the rule at both the Java validation layer and the database column level — two layers of protection.

---

## 2. `Customer.java` — Fixed

The `Customer` form had the opposite problem: `email` was marked `@Column(nullable = false)`, making it required — but the schema says email is optional for customers. This would have caused a runtime error any time someone tried to create a customer without providing an email.

**Fix:** Removed `nullable = false` from the `email` column. The `@Email` annotation was kept so that if an email *is* provided, it still has to be a valid format.

---

## 3. `Employee.java` — Built from scratch

The employee form was completely empty. Each employee needs to be tied to exactly one user account (their login) and an address (where they're based).

- `user` uses `@OneToOne` with `unique = true` — meaning no two employees can share the same user account. The foreign key column in the DB is `user_id`.
- `address` uses `@ManyToOne` — many employees can share the same address, but each employee must have one. The foreign key column is `address_id`.
- `phone` is required (`nullable = false`); `email` is optional (`@Email` only).

---

## 4. `Restaurant.java` — Built from scratch

Also completely empty. A restaurant links to an owner (a `User`) and a physical location (an `Address`), and tracks whether it's currently active and what its price range is (1 = $, 2 = $$, 3 = $$$).

- `user` uses `@ManyToOne` — one user can own multiple restaurants.
- `address` uses `@OneToOne` with `unique = true` — each restaurant has one unique business address.
- `priceRange` uses `@Min(1) @Max(3)` to restrict values, and `columnDefinition = "integer default 1"` to set the database-level default. Just using a Java default wouldn't be enough — the `columnDefinition` actually writes it into the SQL that Hibernate uses to create the table.
- `active` defaults to `true` the same way.
- `email` is optional.

---

## 5. `Product.java` — Built from scratch

Each product (menu item) belongs to one restaurant and has a name and a cost. The cost cannot be negative.

- `restaurant` uses `@ManyToOne` — many products can belong to one restaurant.
- `@Min(0)` on `cost` prevents negative prices.
- `description` is a plain optional field — no constraints needed.
- The schema specifies `ON DELETE CASCADE` for this relationship, meaning if a restaurant is deleted, all its products are automatically removed from the database too. JPA handles this through the foreign key reference.

---

## 6. `OrderStatus.java` — Built from scratch

A simple lookup table that holds one of three possible status labels: `"pending"`, `"in progress"`, or `"delivered"`. Only these values are allowed — no free-form text.

- `@Pattern(regexp = "pending|in progress|delivered")` enforces the allowed values at the validation layer.
- The status is stored as a plain `varchar` in the DB (not a MySQL ENUM), which keeps the database schema flexible while the Java side handles the restriction.

---

## 7. `Order.java` — Built from scratch

An order ties together a customer, a restaurant, and a status. It also optionally holds a 1–5 star rating for the restaurant.

- Three `@ManyToOne` relationships: `restaurant`, `customer`, and `orderStatus` — each stored as a foreign key column in the `orders` table.
- `restaurantRating` is typed as `Integer` (the boxed/object version) rather than `int` (the primitive). This matters because Java primitives can never be `null`, but the rating is optional — so using `Integer` allows the field to simply be absent. `@Min(1) @Max(5)` validates it when it is provided.

---

## 8. `ProductOrder.java` — Built from scratch

This is the line-item record for an order — it logs which product was ordered, how many, and at what unit cost at the time of the order.

Two business rules had to be enforced:
1. The same product can't appear twice in the same order.
2. A product must belong to the same restaurant as the order it's in (enforced at the service layer, not here).

For Rule 1, a **composite unique constraint** was added: `@UniqueConstraint(columnNames = {"product_id", "order_id"})`. This tells the database to reject any row where the combination of `product_id` and `order_id` already exists — making duplicates impossible at the DB level.

- `productQuantity` requires `@Min(1)` — you can't order zero of something.
- `productUnitCost` requires `@Min(0)` — cost can't be negative.
- Column names are explicitly set with `@Column(name = "product_quantity")` etc. to match the snake_case naming the database expects.

---

## Summary Table

| Model | Status | Key Changes |
|---|---|---|
| `User` | Fixed | Added `nullable = false` + `@NotBlank` to `name`, `email`, `password` |
| `Address` | Already correct | No changes needed |
| `Customer` | Fixed | Removed incorrect `nullable = false` from optional `email` |
| `Employee` | Implemented | `@OneToOne` user, `@ManyToOne` address, optional email |
| `Restaurant` | Implemented | `@ManyToOne` user, `@OneToOne` address, price range 1–3, defaults |
| `Product` | Implemented | `@ManyToOne` restaurant, cost min 0 |
| `OrderStatus` | Implemented | `@Pattern` to restrict name to allowed values |
| `Order` | Implemented | Three `@ManyToOne` refs, optional `Integer` rating 1–5 |
| `ProductOrder` | Implemented | Composite unique constraint, quantity min 1, unit cost min 0 |

