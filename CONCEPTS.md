# CONCEPTS.md — Rocket Food Delivery (Module 11)

Three challenging concepts used in the Rocket Food Delivery Spring Boot back-office application.

---

## 1. JPA Entity Relationships (`@ManyToOne` / `@OneToOne`)

**Purpose in the project:**
JPA relationship annotations map Java object associations directly to foreign-key columns in MySQL. For example, an `Order` holds references to a `Restaurant`, a `Customer`, and an `OrderStatus` — each stored as a foreign key in the `orders` table. This allows the application to load fully hydrated objects from the database and display related data (e.g., `order.restaurant.name`) in Thymeleaf templates without writing any SQL.

**Why it was challenging:**
Choosing the correct relationship type (`@ManyToOne` vs `@OneToOne`) and understanding when to use `unique = true` on the join column required careful reading of the ERD. A subtle mistake — such as using `@OneToOne` where `@ManyToOne` was needed — causes Hibernate to generate the wrong schema. Additionally, form submissions only post an ID for the foreign key, so controllers must look up the full entity from its service and re-attach it before saving (the FK enrichment pattern), otherwise Hibernate throws a detached entity error.

**Usage location:**
- `src/main/java/com/rocketFoodDelivery/rocketFood/models/Order.java` — lines 28, 32, 36 (`@ManyToOne` for restaurant, customer, orderStatus)
- `src/main/java/com/rocketFoodDelivery/rocketFood/models/Employee.java` — line 31
- `src/main/java/com/rocketFoodDelivery/rocketFood/models/ProductOrder.java` — lines 29, 33

---

## 2. Spring MVC Form Binding and Validation (`@Valid`, `BindingResult`, `th:field`)

**Purpose in the project:**
Spring MVC's form binding allows a Thymeleaf HTML form to automatically populate a Java model object when the form is submitted. The `@Valid` annotation triggers Jakarta Bean Validation constraints (e.g., `@NotBlank`, `@Min`, `@Email`) declared on entity fields. `BindingResult` captures any validation errors so the controller can return the user to the form with inline error messages rather than crashing. On the template side, `th:field` binds each input to the correct model field and `th:errors` renders the validation message beneath it.

**Why it was challenging:**
The binding pipeline between form, controller, and template has multiple failure points. The model attribute name in `@ModelAttribute` must exactly match the variable passed to the template. Validation errors on foreign-key fields (e.g., a null `user` object) do not map cleanly to field-level errors without careful null checking. Getting `th:classappend` to conditionally add `is-invalid` to Bootstrap inputs and ensuring `BindingResult` always immediately follows `@Valid` (a Spring requirement) both required precise ordering of method parameters.

**Usage location:**
- `src/main/java/com/rocketFoodDelivery/rocketFood/controller/backoffice/UserController.java` — lines 54, 85 (`@Valid`, `BindingResult`)
- All 9 controllers in `src/main/java/com/rocketFoodDelivery/rocketFood/controller/backoffice/`
- All 9 form templates in `src/main/resources/templates/` (e.g., `user/userForm.html`)

---

## 3. Spring Security — Custom Login with `SecurityFilterChain`

**Purpose in the project:**
Spring Security protects all `/backoffice/**` routes so that only authenticated users can access the back office. A `SecurityFilterChain` bean defines which URLs are public (`/`, `/login`, `/error`) and which require authentication. A custom login page at `/login` replaces Spring Security's default form, providing a branded experience consistent with the rest of the application. An in-memory user (`admin` / `admin`) is configured with `BCryptPasswordEncoder` for the password hash so no database user table is required for authentication.

**Why it was challenging:**
Spring Security intercepts requests before they reach controllers, which creates non-obvious debugging scenarios. Configuring a custom login page requires both the `SecurityFilterChain` pointing to `/login` and a separate `@GetMapping("/login")` controller method to render the template — missing either one results in a 500 error (as experienced during development). The CSRF token is automatically included in form POSTs by Spring Security, so the logout button also requires a `<form method="post">` rather than a simple link, which is a common pitfall. Ensuring the `PasswordEncoder` bean is declared separately (not inline) avoids a circular dependency between `SecurityConfig` and `UserDetailsService`.

**Usage location:**
- `src/main/java/com/rocketFoodDelivery/rocketFood/config/SecurityConfig.java` — line 19 (`SecurityFilterChain filterChain`)
- `src/main/java/com/rocketFoodDelivery/rocketFood/controller/HomeController.java` — line 14 (`@GetMapping("/login")`)
- `src/main/resources/templates/login.html`
- `src/main/resources/templates/fragments/nav.html` (logout `<form method="post">`)
