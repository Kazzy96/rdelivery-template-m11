# Rocket Food Delivery — Back Office

A full-stack **Back Office Management System** for a food delivery platform, built with Spring Boot MVC, JPA/Hibernate, and Thymeleaf. It provides a browser-based interface for managing all entities in the Rocket Food Delivery database — users, addresses, restaurants, employees, customers, products, orders, and more.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| ORM | Spring Data JPA / Hibernate |
| Templating | Thymeleaf 3 + Bootstrap 5 |
| Security | Spring Security 6 (form login) |
| Database | MySQL 8 |
| Build Tool | Maven (via `./mvnw` wrapper) |
| Utilities | Lombok, Bean Validation (Jakarta) |
| Dev Tools | Spring Boot DevTools (hot reload) |

---

## Project Structure

```
src/
└── main/
    ├── java/com/rocketFoodDelivery/rocketFood/
    │   ├── RocketFoodApplication.java          # Entry point
    │   ├── DataSeeder.java                     # Optional DB seeder (dev only)
    │   ├── config/
    │   │   └── SecurityConfig.java             # Spring Security config, in-memory admin user
    │   ├── controller/
    │   │   ├── HomeController.java             # / and /login routes
    │   │   ├── advice/
    │   │   │   └── GlobalExceptionHandler.java # 4xx/5xx error pages
    │   │   └── backoffice/
    │   │       ├── AddressController.java
    │   │       ├── CustomerController.java
    │   │       ├── EmployeeController.java
    │   │       ├── OrderController.java
    │   │       ├── OrderStatusController.java
    │   │       ├── ProductController.java
    │   │       ├── ProductOrderController.java
    │   │       ├── RestaurantController.java
    │   │       └── UserController.java
    │   ├── models/                             # JPA entities (9 tables)
    │   ├── repository/                         # Spring Data JPA repositories
    │   ├── service/                            # Service layer (business logic)
    │   └── oop/                               # OOP exercises (BankAccount, etc.)
    └── resources/
        ├── application.properties              # DB connection config
        ├── static/                             # Images and static assets
        └── templates/                          # Thymeleaf HTML templates
            ├── home.html
            ├── login.html
            ├── fragments/nav.html
            ├── address/    customerForm/List
            ├── customer/   customerForm/List
            ├── employee/   employeeForm/List
            ├── order/      orderForm/List
            ├── orderStatus/
            ├── product/
            ├── productOrder/
            ├── restaurant/
            └── user/
```

---

## Installation & Setup

### Prerequisites

- **Java 17** — [Download](https://www.oracle.com/java/technologies/downloads/#java17)
- **MySQL 8+** — [Download](https://dev.mysql.com/downloads/mysql/)
- **VS Code** with the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### 1. Clone the repository

```bash
git clone <repository-url>
cd rdelivery-template-m11
```

### 2. Create the database

```sql
CREATE DATABASE rdelivery;
```

### 3. Configure database credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rdelivery
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Or open `RocketFoodApplication.java` in VS Code and click the **Run Java** button.

Look for this line to confirm a successful start:

```
INFO --- [main] c.r.rocketFood.RocketFoodApplication : Started RocketFoodApplication in X.XXX seconds
```

### 5. Open the app

Navigate to [http://localhost:8080](http://localhost:8080) in your browser.

---

## Environment Variables

This project uses `application.properties` directly rather than environment variables. The relevant configuration keys are:

| Property | Description | Example |
|---|---|---|
| `spring.datasource.url` | MySQL JDBC connection URL | `jdbc:mysql://localhost:3306/rdelivery` |
| `spring.datasource.username` | MySQL username | `root` |
| `spring.datasource.password` | MySQL password | `yourpassword` |
| `spring.jpa.hibernate.ddl-auto` | Schema strategy | `update` (auto-creates/updates tables) |
| `spring.jpa.show-sql` | Log SQL queries | `false` |

> **Note:** Do not commit real credentials to version control. Consider using a `.env` file or OS-level environment variables for production.

---

## Default Login

The app uses Spring Security with a hardcoded in-memory admin user (defined in `SecurityConfig.java`):

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `admin` |

> This is a development-only credential. Replace with a proper user store before any production use.

---

## API Documentation

This project is a **server-side rendered MVC application** — it has no REST API or JSON endpoints. All routes return HTML pages rendered by Thymeleaf.

### Route Structure

All back-office routes follow the same CRUD pattern under `/backoffice/{entity}`:

| Method | Route | Action |
|---|---|---|
| `GET` | `/backoffice/{entity}` | List all records |
| `GET` | `/backoffice/{entity}/new` | Show create form |
| `POST` | `/backoffice/{entity}` | Save new record |
| `GET` | `/backoffice/{entity}/{id}/edit` | Show edit form |
| `POST` | `/backoffice/{entity}/{id}` | Update record |
| `POST` | `/backoffice/{entity}/{id}/delete` | Delete record |

### Supported Entities

| Entity | Base Route |
|---|---|
| Users | `/backoffice/users` |
| Addresses | `/backoffice/addresses` |
| Employees | `/backoffice/employees` |
| Restaurants | `/backoffice/restaurants` |
| Customers | `/backoffice/customers` |
| Products | `/backoffice/products` |
| Order Statuses | `/backoffice/order-statuses` |
| Orders | `/backoffice/orders` |
| Product Orders | `/backoffice/product-orders` |

### Other Routes

| Route | Description |
|---|---|
| `GET /` | Dashboard (requires login) |
| `GET /login` | Login page |
| `POST /login` | Authenticate |
| `POST /logout` | Log out |

---

## Author

Developed as part of the **Rocket Food Delivery** curriculum — Module 11: MVC and Relational Databases.
