# Rocket Delivery Helper

## Overview

This module focuses on building a **Restaurant Management System** using Spring Boot MVC, JPA, and Thymeleaf. You will implement full CRUD operations following the MVC architectural pattern.

---

## Architecture Pattern

This module follows the **Spring MVC Pattern**:

```
View (HTML/Thymeleaf) ↔ Controller → Service → Repository → Database
```

**Implementation Order:**
```
1. Models → 2. Repository → 3. Service → 4. Controller → 5. View
```

> **Note:** Spring Data JPA automatically provides implementations for `save()`, `findAll()`, `findById()`, and `deleteById()`.

---

## Naming Conventions

| Location        | Convention         | Example                      |
|-----------------|--------------------|------------------------------|
| Database Table  | snake_case         | order_statuses, restaurants  |
| Database Column | snake_case         | price_range, user_id         |
| Java Class      | PascalCase         | OrderStatus, Restaurant      |
| Java Variable   | camelCase          | priceRange, userId           |
| Java File Name  | PascalCase.java    | OrderStatus.java             |

---

## Annotation Requirements

Each model (entity class) must include:

### JPA Annotations (Database Mapping)
- `@Entity` - Marks class as database table
- `@Table(name = "table_name")` - Specifies table name
- `@Id` - Marks primary key field
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` - Auto-increment ID
- `@Column(name = "column_name")` - Maps to database column
- `@ManyToOne`, `@OneToOne`, `@OneToMany` - Define relationships
- `@JoinColumn(name = "foreign_key")` - Defines foreign key

### Jakarta Validation Annotations (Data Validation)
- `@NotNull` - Field cannot be null
- `@NotBlank` - String cannot be null, empty, or whitespace
- `@Email` - Validates email format
- `@Min` / `@Max` - Validates numeric range
- `@Size` - Validates string length

### Lombok Annotations (Code Generation)
- `@Data` - Generates getters, setters, toString, equals, hashCode
- `@NoArgsConstructor` - Generates empty constructor
- `@AllArgsConstructor` - Generates constructor with all fields
- `@Builder` - Enables builder pattern

---

## Controller Types

| Annotation       | Purpose                                                              |
|------------------|----------------------------------------------------------------------|
| `@Controller`    | Handles web requests and returns HTML views (with Thymeleaf)         |
| `@RestController`| Creates RESTful APIs that return data (JSON/XML) instead of HTML     |

**For this module, use `@Controller` to return HTML views.**

---

## Restaurant CRUD Operations

Implement complete CRUD functionality for the Restaurant entity.

### Operations Summary

| Operation      | Methods Needed | Total Methods |
|----------------|----------------|---------------|
| CREATE         | 2 (form + save)| 2             |
| READ           | 1 (list)       | 1             |
| UPDATE         | 2 (form + save)| 2             |
| DELETE         | 1 (delete)     | 1             |
| **TOTAL**      |                | **6 methods** |

### Detailed Endpoints

| Operation    | Method | Endpoint                              | Purpose                          |
|--------------|--------|---------------------------------------|----------------------------------|
| List All     | GET    | `/backoffice/restaurants`             | Display all restaurants          |
| Create Form  | GET    | `/backoffice/restaurants/new`         | Show empty form                  |
| Create Save  | POST   | `/backoffice/restaurants`             | Save new restaurant              |
| Edit Form    | GET    | `/backoffice/restaurants/{id}/edit`   | Show pre-filled form             |
| Update Save  | POST   | `/backoffice/restaurants/{id}`        | Update existing restaurant       |
| Delete       | POST   | `/backoffice/restaurants/{id}/delete` | Remove restaurant                |

---

## Development Workflow

### Step 1: Create Models

Define JPA entities with proper annotations and relationships.

**Location:** `src/main/java/com/rocketFoodDelivery/rocketFood/models/`

**Tasks:**

- Complete all model classes
- Add required fields with proper data types
- Include Lombok annotations
- Include JPA annotations
- Add validation annotations
- Define relationships
- Add timestamps

---

### Step 2: Create Repositories

Create repository interfaces that extend JpaRepository.

**Location:** `src/main/java/com/rocketFoodDelivery/rocketFood/repository/`

**Tasks:**

- Create `RestaurantRepository` interface
- Extend `JpaRepository<Restaurant, Integer>`
- Add `@Repository` annotation
- Add custom query methods if needed

---

### Step 3: Create and Test Services

Implement business logic layer and verify functionality.

**Location:** `src/main/java/com/rocketFoodDelivery/rocketFood/service/`

#### 3.1 Create Service Class

**Tasks:**

- Create `RestaurantService` class
- Add `@Service` annotation
- Inject required repositories using constructor injection
- Implement CRUD operation methods
- Add methods for form dropdown data (users, addresses)

#### 3.2 Test Services Using DataSeeder

Verify each service method works before building controllers.

**Location:** `DataSeeder.java`

**Testing Sequence:**

1. **READ ALL** → Uncomment TEST #1 → Run application → Verify console output
2. **CREATE** → Uncomment TEST #3 → Run application → Verify new records in database
3. **UPDATE** → Uncomment TEST #4 → Run application → Verify modifications in database
4. **DELETE** → Uncomment TEST #5 → Run application → Verify deletion in database

**Important:** Test one operation at a time. Only proceed to controllers after all service tests pass.

---

### Step 4: Create Controllers

Build HTTP endpoints that connect services to views.

**Location:** `src/main/java/com/rocketFoodDelivery/rocketFood/controller/`

**Tasks:**

- Create `RestaurantController` class
- Add `@Controller` annotation
- Add `@RequestMapping("/backoffice/restaurants")` for base URL
- Inject `RestaurantService` using constructor injection
- Implement all 6 endpoints listed in the CRUD Operations table above
- Use `Model` to pass data to views
- Use `@PathVariable` for dynamic URL parameters
- Use `@ModelAttribute` for form binding
- Return appropriate view names (Thymeleaf templates)

**Testing:** After implementing each endpoint, test it in your browser using the URLs from the table above.

---

### Step 5: Create HTML Views (Thymeleaf)

Build user interface templates.

**Location:** `src/main/resources/templates/restaurant/`

**Tasks:**

- Create `restaurantList.html` - Display all restaurants in a table with action links
- Create `restaurantForm.html` - Single form for both create and edit operations
- Use Thymeleaf syntax for dynamic content

**Key Thymeleaf Attributes:**

- `th:each` - Loop through collections
- `th:text` - Display text content
- `th:value` - Set input values
- `th:action` - Set form action URL
- `th:href` - Create links
- `th:object` - Bind form to model object
- `th:field` - Bind input to object property

---

## Tips for Success

✅ Follow the implementation order strictly (Models → Repository → Service → Controller → View)  
✅ Test each layer before moving to the next  
✅ Reference existing `Address` and `User` implementations  
✅ Verify data in MySQL database using DBeaver or MySQL Workbench  
✅ Check console logs for errors  
✅ Use browser developer tools to inspect HTML and network requests  
✅ Consult official documentation when stuck  

---

## Resources

- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Spring MVC Documentation](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)
- [Lombok Documentation](https://projectlombok.org/)
- Database Schema: `db_schema_11_v2.txt`
