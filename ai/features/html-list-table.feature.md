# 🤖 AI_FEATURE — HTML List / Table for All Tables

> **Read the global [`ai/ai-spec.md`](../ai-spec.md) before implementing this feature.**
> This feature depends on all controllers being complete — read [`all-controllers.feature.md`](all-controllers.feature.md) first.

---

## Feature Identity

- **Feature Name:** HTML List / Table
- **Related Area:** Frontend — Part 2, Thymeleaf Views

---

## Feature Goal

Implement the list/table Thymeleaf view for each of the 9 entities. Each page renders a Bootstrap-styled table of all records, shows flash messages (success/error), includes a "Create New" button, and provides Edit and Delete actions per row. Empty state is handled gracefully.

---

## Feature Scope

### In Scope (Included)

- 9 list templates: `userList`, `addressList`, `employeeList`, `restaurantList`, `customerList`, `productList`, `orderStatusList`, `orderList`, `productOrderList`
- Bootstrap 5 CDN for styling
- Thymeleaf `th:each` for row iteration
- Flash message display (`successMessage`, `errorMessage`) from redirect attributes
- "Create New" button linking to `/backoffice/{entity}/new`
- Per-row Edit button linking to `/backoffice/{entity}/{id}/edit`
- Per-row Delete button (POST form with `method="post"` and `_method` override via hidden `th:action`)
- Empty state: message shown when the list is empty
- Page title and heading match entity name

### Out of Scope (Excluded)

- Pagination
- Search / filtering
- Sorting column headers
- Inline editing
- Any CSS beyond Bootstrap 5 CDN

---

## Page Structure (all 9 pages follow same layout)

```
<nav> Back Office heading + nav links </nav>
<main class="container py-4">
  <h1>Entity List</h1>
  [successMessage alert]
  [errorMessage alert]
  <a href="/backoffice/{entity}/new">+ Create New</a>
  <table class="table table-striped table-hover">
    <thead> ... column headers ... </thead>
    <tbody>
      <tr th:each="item : ${items}">
        <td> ... cell values ... </td>
        <td> Edit | Delete </td>
      </tr>
      <tr th:if="${#lists.isEmpty(items)}">
        <td colspan="N">No records found.</td>
      </tr>
    </tbody>
  </table>
</main>
```

---

## Column Definitions Per Table

| Template | Model Attr | Columns (header → Thymeleaf expression) |
|---|---|---|
| `userList` | `users` | ID `id`, Name `name`, Email `email` |
| `addressList` | `addresses` | ID `id`, Street `streetAddress`, City `city`, Postal Code `postalCode` |
| `employeeList` | `employees` | ID `id`, Name `user.name`, Email `email`, Phone `phone` |
| `restaurantList` | `restaurants` | ID `id`, Name `name`, Email `email`, Phone `phone`, Price Range `priceRange`, Active `active` |
| `customerList` | `customers` | ID `id`, Name `user.name`, Email `email`, Phone `phone`, Active `active` |
| `productList` | `products` | ID `id`, Name `name`, Restaurant `restaurant.name`, Cost `cost`, Description `description` |
| `orderStatusList` | `orderStatuses` | ID `id`, Name `name` |
| `orderList` | `orders` | ID `id`, Restaurant `restaurant.name`, Customer `customer.user.name`, Status `orderStatus.name`, Rating `restaurantRating` |
| `productOrderList` | `productOrders` | ID `id`, Product `product.name`, Order ID `order.id`, Qty `productQuantity`, Unit Cost `productUnitCost` |

---

## Actions Per Row

Each row has two action buttons in a final "Actions" column:

- **Edit:** `<a th:href="@{/backoffice/{entity}/{id}/edit(id=${item.id})}">Edit</a>` styled as `btn btn-sm btn-warning`
- **Delete:** `<form th:action="@{/backoffice/{entity}/{id}/delete(id=${item.id})}" method="post">` with a `<button type="submit" class="btn btn-sm btn-danger">Delete</button>` — no JavaScript confirm needed

---

## Flash Messages

At the top of the content area, display conditionally:

```html
<div th:if="${successMessage}" class="alert alert-success" th:text="${successMessage}"></div>
<div th:if="${errorMessage}" class="alert alert-danger" th:text="${errorMessage}"></div>
```

---

## Empty State

Inside `<tbody>`, after the `th:each` row, add:

```html
<tr th:if="${#lists.isEmpty(items)}">
  <td colspan="N" class="text-center text-muted">No records found.</td>
</tr>
```

Where `N` = total number of columns including the Actions column.

---

## Tech Constraints (Feature-Level)

- Thymeleaf namespace: `xmlns:th="http://www.thymeleaf.org"`
- Bootstrap 5.3 CDN only — no local CSS files
- Delete uses a plain POST `<form>` — no `@{...?_method=DELETE}` tricks needed since controllers use `@PostMapping("/{id}/delete")`
- All paths use Thymeleaf link expressions `@{...}` — no hardcoded strings

---

## Acceptance Criteria

- [ ] All 9 list templates exist and are valid HTML
- [ ] Each page renders without Thymeleaf errors when the list is empty
- [ ] Each page renders all columns with correct data when records exist
- [ ] Edit button navigates to the correct edit form URL
- [ ] Delete button submits POST to the correct delete URL and redirects back to list
- [ ] Flash messages appear when redirected after create/update/delete
- [ ] Empty state row is shown when list is empty
- [ ] `http://localhost:8080/backoffice/{entity}` returns a rendered table (not a blank page)
