# 🤖 AI_SPEC — Rocket Food Delivery (Module 11)

> **Read this document first before implementing any feature.**
> All feature specs in `./ai/features/` extend the rules defined here.

---

## Project Identity

- **Project Name:** Rocket Food Delivery
- **Short Description:**
  A Java Spring Boot back-office application for Rocket Food Delivery that implements a 9-table MySQL relational database from a provided ERD and exposes full CRUD operations on every table through a Thymeleaf-based back office at `/backoffice/{table-name}`.
- **Project Type:** Java Spring Boot MVC application (server-rendered with Thymeleaf) backed by a MySQL relational database.

---

## Goal and Scope

### Goal

Implement the Rocket Food Delivery database exactly as defined in the ERD using Spring Boot + JPA/Hibernate, and deliver a proof-of-concept Restaurant back office that allows internal staff to list, create, update, and delete records for any of the 9 entities, with all changes persisting in MySQL.

The module is delivered in **two parts**:

1. **Part 1 — Java OOP Exercises:** A standalone banking class hierarchy (BankAccount, SavingsAccount, CheckingAccount, BankCustomer) demonstrating Java fundamentals: classes, inheritance, polymorphism, composition, and method overriding.
2. **Part 2 — Rocket Food Delivery Server:** A Spring Boot application built on top of the `rdelivery-template-m11` template, containing 9 JPA entities, repositories, services, controllers, and Thymeleaf views for the back office.

### In Scope (Build Now)

- Java OOP banking exercises (BankAccount + SavingsAccount + CheckingAccount + BankCustomer + extended transaction history + reports + interest update + overdraft limits) with `main()` demos for each feature.
- 9 JPA entity classes matching the provided ERD exactly (column names, data types, relationships).
- Spring Data JPA repository interface for each entity.
- Service layer for each entity holding business logic and CRUD operations.
- Spring MVC controllers exposing the back-office routes.
- Thymeleaf views: list/table page per entity and create/edit form page per entity.
- Full CRUD (Create, Read, Update, Delete) for every entity, persisting to MySQL.
- `application.properties` excluded from version control via `.gitignore`.
- Project documentation: `README.md`, `CONCEPTS.md`, AI specs and per-feature specs under `./ai/`.

### Out of Scope (Do NOT Build)

- No customer-facing front end, no public API, no JSON/REST endpoints — this is a server-rendered back office only.
- No MERN-stack code, no React, no Node.js, no MongoDB — those belonged to the previous module.
- No authentication / authorization on back-office routes (unless implemented as the optional **HTML Login** extra mile).
- No ERD redesign — the ERD is the source of truth and must be followed exactly.
- No advanced Spring features (Spring Security beyond the optional login extra mile, Spring Cloud, microservices, messaging, caching, etc.).
- No deployment / containerization / CI configuration.
- No features not listed in the Requirement Checklist — the checklist is the source of truth.

---

## Users and Use Cases

- **Rocket Food Delivery internal staff (back-office user):** Navigate to `/backoffice/{table-name}` to list records of any of the 9 tables. From the list page they can open a create form, edit an existing record, or delete a record. All changes are immediately written to MySQL and visible on refresh.
- **Developer / coach (verifier):** Open DBeaver against the MySQL database to confirm that schema and data changes performed through the back office have actually persisted.

---

## Feature Index (Links Only)

Each feature has its own dedicated spec file under `./ai/features/`.

**Part 1 — Java OOP Exercises**

- `./ai/features/oop-bank-account.feature.md`
- `./ai/features/oop-savings-account.feature.md`
- `./ai/features/oop-checking-account.feature.md`
- `./ai/features/oop-bank-customer.feature.md`
- `./ai/features/oop-extend-bank-account.feature.md`
- `./ai/features/oop-report.feature.md`
- `./ai/features/oop-interest.feature.md`
- `./ai/features/oop-limits.feature.md`

**Part 2 — Rocket Food Delivery Server**

- `./ai/features/all-schemas.feature.md`
- `./ai/features/all-repositories.feature.md`
- `./ai/features/all-services.feature.md`
- `./ai/features/all-controllers.feature.md`
- `./ai/features/html-list-table.feature.md`
- `./ai/features/html-form-all-tables.feature.md`

---

## Pages / Screens / Routes (Project Map)

The Spring Boot back office exposes the following route pattern for every one of the 9 ERD tables, where `{table-name}` is the entity name (e.g. `restaurant`, `customer`, `order`, etc.).

- `GET /backoffice/{table-name}` — list/table page showing all records for the entity, with action links per row (view/edit/delete).
- `GET /backoffice/{table-name}/new` — empty form to create a new record.
- `POST /backoffice/{table-name}/new` — submit handler that creates the record and redirects back to the list.
- `GET /backoffice/{table-name}/{id}` — form pre-populated with the existing record, used for editing.
- `POST /backoffice/{table-name}/{id}` — submit handler that updates the record and redirects back to the list.
- `POST /backoffice/{table-name}/{id}/delete` — delete handler that removes the record and redirects back to the list.

The application runs on `http://localhost:8080`.

---

## Data and Models (Simple)

### Database

- **Type:** MySQL relational database, accessed via JPA/Hibernate.
- **Schema source:** Provided ERD with **9 tables**. Column names, data types, and relationships must match the ERD **exactly** — any deviation is a defect.
- **Connection settings:** Defined in `src/main/resources/application.properties`. This file is **never** committed (it is listed in `.gitignore`).
- **Verification:** The schema and data are inspected with DBeaver to confirm CRUD operations actually persist.

### Entities (high level)

Each of the 9 ERD tables maps to a JPA `@Entity` class with:

- A primary key field annotated with `@Id` and `@GeneratedValue` where appropriate.
- Columns with `@Column` (nullable, unique, length, etc., as specified in the ERD).
- Relationships (`@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany`) matching the ERD cardinalities exactly.
- Jakarta validation annotations (`@NotNull`, `@Email`, `@Size`, etc.) where the data calls for them.
- Lombok annotations to remove boilerplate (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`).

The exhaustive entity list and field-by-field definitions live in `./ai/features/all-schemas.feature.md` and must trace back to the ERD.

---

## Tech Stack and Tools

This module uses an **entirely new technology stack**. No MERN-stack code is permitted.

### Backend

- **Java 17** — programming language.
- **Spring Boot** — web application framework using the MVC architecture.
- **Spring Data JPA / Hibernate** — Object-Relational Mapping between Java entities and database tables.
- **Maven** — build tool and dependency management.
- **Lombok** — boilerplate code reduction (getters, setters, constructors).

### Database

- **MySQL** — relational SQL database.
- **DBeaver** — database client used to visualize and verify the schema and data (not a runtime dependency).

### Frontend (server-rendered)

- **Thymeleaf** — server-side HTML template engine used for back-office pages.
- Plain HTML and minimal CSS — no JavaScript frameworks.

---

## Repository Structure

The project is bootstrapped from the **`rdelivery-template-m11`** template — do **not** build from scratch. Build the database schema, entities, repositories, services, controllers, and views on top of that foundation.

Expected high-level layout:

- `/src/main/java/.../entity` — JPA entity classes (one per ERD table).
- `/src/main/java/.../repository` — Spring Data JPA repository interfaces.
- `/src/main/java/.../service` — service classes containing business logic.
- `/src/main/java/.../controller` — Spring MVC controllers for the back-office routes.
- `/src/main/resources/templates/backoffice/` — Thymeleaf views (list and form pages per entity).
- `/src/main/resources/application.properties` — local DB credentials (**git-ignored**).
- `/docs/ai/AI_SPEC.md` — this document.
- `/docs/ai/features/` — per-feature specification files.
- `/README.md` — setup and run instructions for someone with no prior knowledge of the project.
- `/CONCEPTS.md` — 3 challenging concepts demonstrated in the recorded video.
- `/LeetCode-Challenges/<challenge-name>.png` — LeetCode solution screenshots.

---

## Rules for the AI

- **Read this AI_SPEC and the relevant feature spec before generating any code.** If they conflict with the **Requirement Checklist**, the checklist wins.
- **Follow the ERD exactly** — column names, data types, and relationships are not negotiable.
- **Use the provided template** (`rdelivery-template-m11`) — do not regenerate the Spring Boot scaffold.
- **Stick to the allowed tech stack** — Java 17, Spring Boot, MySQL, JPA/Hibernate, Thymeleaf, Lombok, Maven. Do not introduce React, Node, Mongo, Spring Security (unless the HTML Login extra mile is in scope), or any other library not listed.
- **Keep code junior-friendly:** clear naming, simple methods, standard Spring patterns. Avoid clever tricks, reflection, custom annotations, or advanced design patterns.
- **Respect the layering:**
  - Controllers handle HTTP, model binding, and view selection — no business logic, no direct repository calls when a service exists.
  - Services hold business logic and orchestrate repositories.
  - Repositories extend `JpaRepository` and only define data access.
  - Entities only hold data and JPA/validation annotations.
- **Use Lombok** to reduce boilerplate; do not hand-write getters/setters when an annotation suffices.
- **Never commit credentials.** `application.properties` must be listed in `.gitignore`.
- **Reuse existing files** in the template before creating new ones. Place new files in the package that matches their layer.
- **Do not invent features** that are not in the Requirement Checklist or in a feature spec.
- **Branching:** work on `feature/*` branches created from `dev`, merge back into `dev`, then `dev → main`. Never commit directly to `main` — only `main` is graded.

---

## How to Run / Test the Project

**Prerequisites:** Java 17, Maven, MySQL running locally, DBeaver (optional, for verification).

**Setup**

1. Clone the repository (cloned from `rdelivery-template-m11`).
2. Create a local MySQL database for the project.
3. Create `src/main/resources/application.properties` with the local datasource URL, username, and password. **This file must not be committed.**
4. Run `mvn clean install` to download dependencies and compile.

**Run**

- Start the application with `mvn spring-boot:run` (or run the main class from your IDE).
- The app listens on `http://localhost:8080`.

**Test the back office**

- Open `http://localhost:8080/backoffice/{table-name}` for each of the 9 tables.
- Create, edit, and delete records through the UI.
- Open DBeaver and confirm the changes are reflected in MySQL.

**Run the OOP exercises**

- Each OOP feature has a `main` method demo (see the per-feature specs). Run them directly from the IDE or with `java`.

---

## Definition of Done

The project is complete when **all** of the following are true. The **Requirement Checklist** is the line-by-line source of truth — if anything below conflicts with it, the checklist wins.

**Project setup**

- [ ] GitHub repository is **private** with all coaches added as collaborators.
- [ ] Project is cloned from `rdelivery-template-m11` (not built from scratch).
- [ ] `application.properties` is listed in `.gitignore` and **never** committed.
- [ ] Branching model is followed: `feature/*` → `dev` → `main`. Only `main` is graded.

**AI-native specifications**

- [ ] `AI_SPEC.md` exists at the documented location and explains the whole project.
- [ ] Every feature listed in the Feature Index has its own spec file under `./ai/features/`.

**Part 1 — Java OOP exercises**

- [ ] `BankAccount` class with `accountNumber`, `balance`, constructor, `deposit`, `withdraw` (only allowed if `amount ≤ balance`), and a `main` demo.
- [ ] `SavingsAccount extends BankAccount` with `interestRate`, constructor, `deposit` overridden to add interest, and a `main` demo.
- [ ] `CheckingAccount extends BankAccount` with `overdraftLimit`, constructor, `withdraw` overridden to allow overdraft up to the limit, and a `main` demo.
- [ ] `BankCustomer` with `name` and a collection of `BankAccount`, `addAccount`, `totalBalance`, and a `main` demo.
- [ ] `BankAccount` extended with a transaction list, `recordTransaction`, `getTransactionHistory`, and `deposit`/`withdraw` updated to record transactions.
- [ ] `BankCustomer.generateReport()` summarizing all accounts and balances; `toString()` overridden in every account class.
- [ ] `SavingsAccount.updateInterestRate(double)` plus a demo of changing the rate and depositing.
- [ ] `CheckingAccount.withdraw` denies any withdrawal where `amount > balance + overdraftLimit`, with a demo of the rejection.

**Part 2 — Rocket Food Delivery Server**

- [ ] All 9 JPA entities exist with correct field names, data types, JPA annotations (`@Id`, `@Column`, `@GeneratedValue`, `nullable`, `unique`, `length`), Jakarta validation annotations (`@NotNull`, `@Email`, `@Size`, etc.), Lombok annotations, and ERD-accurate relationships.
- [ ] All 9 repositories are interfaces extending `JpaRepository`, in the correct package, with consistent return types and any required Spring Data query methods.
- [ ] All 9 services are annotated (`@Service`), use dependency injection, hold the business logic (not the controllers or repositories), implement full CRUD, and handle missing/invalid data appropriately.
- [ ] All 9 controllers are annotated (`@Controller`, `@RequestMapping`, `@GetMapping`, `@PostMapping`), expose the routes listed in **Pages / Screens / Routes**, bind models to Thymeleaf views, handle form submissions and redirects, and surface validation errors back to the form.
- [ ] List/table view per entity: Thymeleaf page with proper structure and namespace, iterates over the collection, displays the required columns, includes per-row actions (view/update/delete), and handles the empty state.
- [ ] Form view per entity: Thymeleaf page with proper form binding, all required fields with correct labels and input types, supports both create and update flows (pre-loads existing values when editing), displays validation errors, submits to the correct route, and redirects appropriately.
- [ ] All routes are reachable at `http://localhost:8080/backoffice/{table-name}`, `/backoffice/{table-name}/new`, and `/backoffice/{table-name}/{id}`.
- [ ] All CRUD changes persist in MySQL and are verifiable in DBeaver.

**Documentation & deliverables**

- [ ] `README.md` explains the project clearly to someone with no prior knowledge of the setup.
- [ ] `CONCEPTS.md` lists 3 challenging concepts; recorded video demonstrates understanding.
- [ ] LeetCode challenge solutions are saved to `./LeetCode-Challenges/<challenge-name>.png`, with the recorded reasoning video.
- [ ] Technical Demonstration and Code Overview video is recorded.

**Professional expectations**

- [ ] Coaches receive a response within **24 hours** when contacted.
- [ ] At least **2 progress updates per week**.
- [ ] At least **1 project review per week** scheduled before Friday.
- [ ] Project submitted through the platform by **Friday 11:59 PM** of the deadline week. Submission Summary is **not** committed to GitHub — it is submitted on the platform.
