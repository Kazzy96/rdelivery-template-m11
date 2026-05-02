# 🤖 AI_FEATURE — All Controllers

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on all services being complete — read [`all-services.feature.md`](all-services.feature.md) first.

---

## Feature Identity

- **Feature Name:** All Controllers
- **Related Area:** Backend — Part 2, Spring Boot Server

---

## Feature Goal

Create a `@Controller` class for each of the 9 entities. Each controller maps HTTP routes to service operations, binds form data to entity objects, passes data to Thymeleaf views via the `Model`, and handles validation errors and edge cases with user-facing flash messages.

---

## Feature Scope

### In Scope (Included)

- 9 controller classes in `com.rocketFoodDelivery.rocketFood.controller.backoffice`
- Routes under `/backoffice/{entity-plural}` (see route table below)
- Standard CRUD routes: list (GET), new form (GET `/new`), create (POST), edit form (GET `/{id}/edit`), update (POST `/{id}`), delete (POST `/{id}/delete`)
- Model population for Thymeleaf views (entity object + FK dropdown lists where needed)
- `@Valid` + `BindingResult` for form validation — redisplay form on error
- Flash messages via `RedirectAttributes` for success and error feedback
- `DataIntegrityViolationException` handling for constraint violations (e.g. delete with FK references)
- FK enrichment: for entities with `@ManyToOne`/`@OneToOne` relations, look up the full related entity by id after model binding and set it before saving

### Out of Scope (Excluded)

- No business logic in controllers — that belongs in services
- No direct repository calls — always go through services
- No REST/JSON endpoints — all routes return view names or redirects
- No Spring Security / authentication
- No pagination

---

## Route & Template Table

| Controller | Base Route | List Template | Form Template | Model Attribute |
|---|---|---|---|---|
| `UserController` | `/backoffice/users` | `user/userList` | `user/userForm` | `user` |
| `AddressController` | `/backoffice/addresses` | `address/addressList` | `address/addressForm` | `address` |
| `EmployeeController` | `/backoffice/employees` | `employee/employeeList` | `employee/employeeForm` | `employee` |
| `RestaurantController` | `/backoffice/restaurants` | `restaurant/restaurantList` | `restaurant/restaurantForm` | `restaurant` |
| `CustomerController` | `/backoffice/customers` | `customer/customerList` | `customer/customerForm` | `customer` |
| `ProductController` | `/backoffice/products` | `product/productList` | `product/productForm` | `product` |
| `OrderStatusController` | `/backoffice/order-statuses` | `orderStatus/orderStatusList` | `orderStatus/orderStatusForm` | `orderStatus` |
| `OrderController` | `/backoffice/orders` | `order/orderList` | `order/orderForm` | `order` |
| `ProductOrderController` | `/backoffice/product-orders` | `productOrder/productOrderList` | `productOrder/productOrderForm` | `productOrder` |

---

## FK Dropdown Model Attributes

For entities with `@ManyToOne` or `@OneToOne` relationships, the controller must add dropdown lists to the model for both the new form and the edit form (and re-add them when redisplaying on validation error):

| Controller | Extra Model Attributes |
|---|---|
| `RestaurantController` | `users` (UserService), `addresses` (AddressService) |
| `CustomerController` | `users` (UserService), `addresses` (AddressService) |
| `EmployeeController` | `users` (UserService), `addresses` (AddressService) |
| `ProductController` | `restaurants` (RestaurantService) |
| `OrderController` | `restaurants`, `customers` (CustomerService), `orderStatuses` (OrderStatusService) |
| `ProductOrderController` | `products` (ProductService), `orders` (OrderService) |

---

## FK Enrichment Pattern

Thymeleaf form fields bind FK ids as nested properties (e.g., `user.id`, `address.id`). Spring creates a shallow nested object (e.g., `new User()` with only `id` set). Before calling `service.save()`, the controller must resolve the full entity:

```java
// Example for Restaurant POST
userService.findById(restaurant.getUser().getId()).ifPresent(restaurant::setUser);
addressService.findById(restaurant.getAddress().getId()).ifPresent(restaurant::setAddress);
restaurantService.save(restaurant);
```

---

## Standard CRUD Flow Per Controller

### GET `/backoffice/{entity}` — List
1. Call `service.findAll()`
2. Add result list to model with the plural entity name (e.g., `"addresses"`)
3. Return list template name
4. On exception: flash error, redirect to `/backoffice`

### GET `/backoffice/{entity}/new` — New Form
1. Add `new Entity()` to model under the entity attribute name
2. Add any FK dropdown lists to model
3. Return form template name

### POST `/backoffice/{entity}` — Create
1. Receive `@Valid @ModelAttribute` + `BindingResult`
2. If errors: re-add FK dropdown lists to model, return form template
3. Enrich FK fields (look up full related entities)
4. Call `service.save(entity)`, flash success
5. On `DataIntegrityViolationException`: flash constraint error
6. Redirect to list

### GET `/backoffice/{entity}/{id}/edit` — Edit Form
1. Call `service.findById(id)` → if empty, flash error + redirect to list
2. Add found entity to model
3. Add FK dropdown lists to model
4. Return form template

### POST `/backoffice/{entity}/{id}` — Update
1. Receive `@Valid @ModelAttribute` + `BindingResult`
2. If errors: set `entity.id = id`, re-add FK dropdowns, return form template
3. Set `entity.id = id`
4. Enrich FK fields
5. Call `service.save(entity)`, flash success
6. On `DataIntegrityViolationException`: flash constraint error
7. Redirect to list

### POST `/backoffice/{entity}/{id}/delete` — Delete
1. Call `service.deleteById(id)`, flash success
2. On `DataIntegrityViolationException`: flash "Cannot delete: referenced by other records"
3. Redirect to list

---

## Validation & Error Handling

- Bean Validation (`@Valid`) on the `@ModelAttribute` entity — `BindingResult` captures field errors
- On validation error: form is redisplayed with the same bound object (errors shown by Thymeleaf in next step)
- `DataIntegrityViolationException` from Spring Data for FK constraint violations and unique constraint violations
- General `Exception` catch-all for unexpected errors (logs message in flash)

---

## Tech Constraints (Feature-Level)

- Package: `com.rocketFoodDelivery.rocketFood.controller.backoffice`
- Annotation: `@Controller` (not `@RestController`)
- `@RequestMapping` at class level for base route
- `@Autowired` field injection is acceptable for controllers (matches existing template pattern)
- `@Valid` must precede `@ModelAttribute` parameter
- `BindingResult` must immediately follow the `@ModelAttribute` parameter
- Do not call repositories directly from controllers

---

## Acceptance Criteria

- [ ] All 9 controller classes exist in `controller/backoffice` package
- [ ] Each is annotated `@Controller` with `@RequestMapping("/backoffice/{entity-plural}")`
- [ ] Each exposes: GET list, GET `/new`, POST create, GET `/{id}/edit`, POST `/{id}` update, POST `/{id}/delete`
- [ ] GET new/edit adds correct model attributes including FK dropdown lists where applicable
- [ ] POST create/update uses `@Valid` + `BindingResult` and redisplays form on error
- [ ] POST create/update enriches FK fields before calling save
- [ ] POST delete catches `DataIntegrityViolationException` and shows a meaningful error message
- [ ] Spring Boot starts without errors after all controllers are in place
- [ ] Navigating to `/backoffice/users` returns HTTP 200 (even with empty view)

---

## Notes for the AI

- `AddressController` already has a `listAddresses()` implementation — keep it, add remaining methods
- `CustomerController` already has `deleteCustomer()` — keep it, add remaining methods
- The other 7 controllers are complete stubs — implement from scratch
- For entities with `int id` (not `Integer`), `entity.setId(id)` in the update handler ensures the correct record is updated
- `findAllByOrderByIdDesc()` is called by the service's `findAll()` — controllers just call `service.findAll()`
