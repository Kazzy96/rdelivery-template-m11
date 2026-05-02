# 🤖 AI_FEATURE — HTML Form for All Tables

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on all controllers being complete — read [`all-controllers.feature.md`](all-controllers.feature.md) first.

---

## Feature Identity

- **Feature Name:** HTML Form (Create / Update)
- **Related Area:** Frontend — Part 2, Thymeleaf Views

---

## Feature Goal

Implement the create/edit Thymeleaf form for each of the 9 entities. A single form template handles both create (`GET /new` → `POST /`) and update (`GET /{id}/edit` → `POST /{id}`) by checking whether the entity id is 0 or not. Each form binds to the entity model attribute, shows validation errors per field, pre-fills values on edit, and has a Cancel link back to the list.

---

## Feature Scope

### In Scope (Included)

- 9 form templates: `userForm`, `addressForm`, `employeeForm`, `restaurantForm`, `customerForm`, `productForm`, `orderStatusForm`, `orderForm`, `productOrderForm`
- Bootstrap 5 CDN for styling
- `th:object` form binding on the entity model attribute
- `th:field` on every input/select for automatic value binding and id/name generation
- Dynamic form `th:action` — POST to `/backoffice/{entity}` on create, POST to `/backoffice/{entity}/{id}` on update
- Dynamic page title and heading: "Create X" vs "Edit X"
- Per-field validation error display using `th:errors` and Bootstrap `is-invalid` / `invalid-feedback` classes
- FK dropdowns (HTML `<select>`) for related entities, bound with `th:field` and `th:each` option iteration
- Cancel link back to the list page
- Submit button labelled "Create" or "Update" depending on mode

### Out of Scope (Excluded)

- No client-side JS validation
- No file uploads
- No multi-step forms
- No auth / CSRF tokens beyond what Spring Boot provides automatically

---

## Form Field Definitions

### `userForm` — entity: `user`, route: `/backoffice/users`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Name | text | `*{name}` | required, not blank |
| Email | email | `*{email}` | required, not blank |
| Password | password | `*{password}` | required, not blank |

### `addressForm` — entity: `address`, route: `/backoffice/addresses`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Street Address | text | `*{streetAddress}` | required |
| City | text | `*{city}` | required |
| Postal Code | text | `*{postalCode}` | required |

### `employeeForm` — entity: `employee`, route: `/backoffice/employees`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| User | select | `*{user.id}` | FK dropdown from `users` |
| Address | select | `*{address.id}` | FK dropdown from `addresses` |
| Phone | text | `*{phone}` | required |
| Email | email | `*{email}` | optional |

### `restaurantForm` — entity: `restaurant`, route: `/backoffice/restaurants`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Name | text | `*{name}` | required |
| User | select | `*{user.id}` | FK dropdown from `users` |
| Address | select | `*{address.id}` | FK dropdown from `addresses` |
| Phone | text | `*{phone}` | required |
| Email | email | `*{email}` | optional |
| Price Range | number (1–3) | `*{priceRange}` | min 1, max 3 |
| Active | checkbox | `*{active}` | boolean |

### `customerForm` — entity: `customer`, route: `/backoffice/customers`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| User | select | `*{user.id}` | FK dropdown from `users` |
| Address | select | `*{address.id}` | FK dropdown from `addresses` |
| Phone | text | `*{phone}` | required |
| Email | email | `*{email}` | optional |
| Active | checkbox | `*{active}` | boolean |

### `productForm` — entity: `product`, route: `/backoffice/products`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Restaurant | select | `*{restaurant.id}` | FK dropdown from `restaurants` |
| Name | text | `*{name}` | required |
| Description | textarea | `*{description}` | optional |
| Cost | number | `*{cost}` | min 0 |

### `orderStatusForm` — entity: `orderStatus`, route: `/backoffice/order-statuses`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Name | select | `*{name}` | one of: pending, in progress, delivered |

### `orderForm` — entity: `order`, route: `/backoffice/orders`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Restaurant | select | `*{restaurant.id}` | FK dropdown from `restaurants` |
| Customer | select | `*{customer.id}` | FK dropdown from `customers` |
| Order Status | select | `*{orderStatus.id}` | FK dropdown from `orderStatuses` |
| Rating | number (1–5) | `*{restaurantRating}` | optional, min 1 max 5 |

### `productOrderForm` — entity: `productOrder`, route: `/backoffice/product-orders`
| Field | Input Type | `th:field` | Validation |
|---|---|---|---|
| Product | select | `*{product.id}` | FK dropdown from `products` |
| Order | select | `*{order.id}` | FK dropdown from `orders` |
| Quantity | number | `*{productQuantity}` | min 1 |
| Unit Cost | number | `*{productUnitCost}` | min 0 |

---

## Create vs Update Behaviour

Both modes use the same template. The controller determines behaviour:

- **Create (`id == 0`):** form action is `/backoffice/{entity}` (POST), title is "Create X"
- **Update (`id > 0`):** form action is `/backoffice/{entity}/{id}` (POST), title is "Edit X"

Thymeleaf expressions:
```html
<form th:action="${entity.id == 0 ? '/backoffice/entities' : '/backoffice/entities/' + entity.id}" method="post">
<h1 th:text="${entity.id == 0 ? 'Create Entity' : 'Edit Entity'}"></h1>
<button th:text="${entity.id == 0 ? 'Create' : 'Update'}"></button>
```

---

## Validation Error Display

Each field uses Bootstrap `is-invalid` class and `th:errors` for the error message:

```html
<input type="text" th:field="*{name}"
       th:classappend="${#fields.hasErrors('name')} ? 'is-invalid'">
<div class="invalid-feedback" th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></div>
```

---

## FK Dropdown Pattern

```html
<select th:field="*{user.id}" class="form-select">
    <option value="">-- Select User --</option>
    <option th:each="u : ${users}" th:value="${u.id}" th:text="${u.name}"></option>
</select>
```

---

## Tech Constraints (Feature-Level)

- `th:object="${entityName}"` on the `<form>` tag
- `th:field` uses `*{fieldName}` shorthand (not `${entity.fieldName}`)
- FK fields bind to the nested id: `*{user.id}`, `*{address.id}`, etc.
- Form method is always `post` — no `PUT`/`PATCH`
- Cancel link: `<a th:href="@{/backoffice/{entity}}">Cancel</a>`

---

## Acceptance Criteria

- [ ] All 9 form templates exist and are valid HTML with Thymeleaf namespace
- [ ] Each form renders for create (blank) and edit (pre-filled) without errors
- [ ] Submitting with invalid data redisplays the form with field-level error messages
- [ ] FK dropdowns are populated with existing records
- [ ] Submitting valid data on create navigates to list with success flash
- [ ] Submitting valid data on update navigates to list with success flash
- [ ] Cancel link returns to the correct list page
- [ ] `http://localhost:8080/backoffice/{entity}/new` returns HTTP 200 with form
- [ ] `http://localhost:8080/backoffice/{entity}/{id}/edit` returns HTTP 200 with pre-filled form
