# Rocket Food Delivery - Module 11: MVC and Relational Databases

This module focuses on building a complete **Restaurant Management System** using **Spring Boot MVC**, **JPA**, and **Thymeleaf**. You will implement full CRUD operations following the MVC architectural pattern.

---

## Prerequisites

- **Java**
- **MySQL**
- **VS Code** with Java extensions

All other dependencies are already defined in the `pom.xml` file.

---

## Initial Setup

### 1. Database Configuration

1. Ensure environment variables are properly set
2. Log into your MySQL console
3. Create a database named `rdelivery`:

### 2. Project Configuration

1. Clone the project to your local machine
2. Open the project in VS Code
3. Edit `src/main/resources/application.properties`:
   - Update `<database_name>` with `rdelivery`
   - Update `<username>` with your MySQL username
   - Update `<password>` with your MySQL password

### 3. Verify Installation

1. Open `RocketFoodApplication.java`
2. Run the application (click "Run Java" arrow or run `mvn spring-boot:run` in terminal)
3. Look for success message:

   ```java
   INFO 24016 --- [main] c.r.rocketFood.RocketFoodApplication : Started RocketFoodApplication in 2.726 seconds
   ```
