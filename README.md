# SpendWise API

SpendWise API is a secure personal finance REST API built with Java, Spring Boot, Spring Security, JWT authentication, PostgreSQL, and Spring Data JPA. It helps users manage income, expenses, categories, monthly budgets, and financial summaries.

This project was built as a backend portfolio project to demonstrate real-world Spring Boot API development, authentication, database relationships, protected routes, and clean MVC architecture.

---

## Features

* User registration and login
* JWT-based authentication
* BCrypt password hashing
* Protected APIs using Bearer token
* Category management
* Income and expense transaction management
* Budget management
* Monthly financial summary
* Category-wise expense breakdown
* Transaction filtering by date, category, and type
* User-specific data access
* PostgreSQL database integration
* Spring Data JPA repositories
* MVC structure with service and repository layers
* Request/response DTOs for clean API design

---

## Tech Stack

* Java 25
* Spring Boot 4.0.6
* Spring Security
* JWT / OAuth2 Resource Server
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok
* Bean Validation
* Postman

---

## Project Structure

```text
src/main/java/org/example/spendwiseapi
│
├── SpendWiseApiApplication.java
│
├── config
│   ├── JwtConfig.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── BudgetController.java
│   ├── CategoryController.java
│   ├── SummaryController.java
│   └── TransactionController.java
│
├── dto
│   ├── AuthResponse.java
│   ├── BudgetRequest.java
│   ├── BudgetResponse.java
│   ├── CategoryRequest.java
│   ├── CategoryResponse.java
│   ├── LoginRequest.java
│   ├── MonthlySummaryResponse.java
│   ├── RegisterRequest.java
│   ├── TransactionRequest.java
│   └── TransactionResponse.java
│
├── model
│   ├── AppUser.java
│   ├── Budget.java
│   ├── Category.java
│   ├── Role.java
│   ├── Transaction.java
│   └── TransactionType.java
│
├── repository
│   ├── BudgetRepository.java
│   ├── CategoryRepository.java
│   ├── TransactionRepository.java
│   └── UserRepository.java
│
└── service
    ├── AuthService.java
    ├── BudgetService.java
    ├── CategoryService.java
    ├── CurrentUserService.java
    ├── JwtService.java
    ├── SummaryService.java
    └── TransactionService.java
```

---

## API Modules

### Authentication

| Method | Endpoint             | Description                     |
| ------ | -------------------- | ------------------------------- |
| POST   | `/api/auth/register` | Register a new user             |
| POST   | `/api/auth/login`    | Login user and return JWT token |

### Categories

| Method | Endpoint               | Description                          |
| ------ | ---------------------- | ------------------------------------ |
| POST   | `/api/categories`      | Create category                      |
| GET    | `/api/categories`      | Get all categories of logged-in user |
| PUT    | `/api/categories/{id}` | Update category                      |
| DELETE | `/api/categories/{id}` | Delete category                      |

### Transactions

| Method | Endpoint                                                    | Description                          |
| ------ | ----------------------------------------------------------- | ------------------------------------ |
| POST   | `/api/transactions`                                         | Create income or expense transaction |
| GET    | `/api/transactions`                                         | Get all transactions                 |
| GET    | `/api/transactions?categoryId=1`                            | Filter transactions by category      |
| GET    | `/api/transactions?type=EXPENSE`                            | Filter transactions by type          |
| GET    | `/api/transactions?startDate=2026-05-01&endDate=2026-05-31` | Filter transactions by date range    |
| PUT    | `/api/transactions/{id}`                                    | Update transaction                   |
| DELETE | `/api/transactions/{id}`                                    | Delete transaction                   |

### Budgets

| Method | Endpoint            | Description                          |
| ------ | ------------------- | ------------------------------------ |
| POST   | `/api/budgets`      | Create monthly budget for a category |
| GET    | `/api/budgets`      | Get all budgets                      |
| PUT    | `/api/budgets/{id}` | Update budget                        |
| DELETE | `/api/budgets/{id}` | Delete budget                        |

### Summary

| Method | Endpoint                                 | Description                                                      |
| ------ | ---------------------------------------- | ---------------------------------------------------------------- |
| GET    | `/api/summary/monthly?year=2026&month=5` | Get monthly income, expense, balance, and category-wise expenses |

---

## Authentication Flow

1. User registers using `/api/auth/register`.
2. User logs in using `/api/auth/login`.
3. API returns a JWT token.
4. User sends the token in Postman or frontend using Bearer Token authorization.
5. Protected APIs only work when a valid token is provided.

Example header:

```http
Authorization: Bearer your_jwt_token_here
```

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/spendwise-api.git
cd spendwise-api
```

### 2. Create PostgreSQL database

Open pgAdmin or PostgreSQL terminal and create a database:

```sql
CREATE DATABASE spendwise_db;
```

### 3. Configure database

Open:

```text
src/main/resources/application.properties
```

Add your PostgreSQL username and password:

```properties
spring.application.name=spendwise

spring.datasource.url=jdbc:postgresql://localhost:5432/spendwise_db
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

app.jwt.secret=your_base64_secret_key_here
app.jwt.expiration-minutes=120
```

### 4. Generate JWT secret

Use this simple Java code to generate a Base64 secret:

```java
import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {
    public static void main(String[] args) {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        System.out.println(Base64.getEncoder().encodeToString(key));
    }
}
```

Copy the output and paste it into:

```properties
app.jwt.secret=your_generated_secret_here
```

### 5. Run the application

Using IntelliJ:

```text
Run SpendWiseApiApplication.java
```

Using terminal:

```bash
mvn spring-boot:run
```

The API will start on:

```text
http://localhost:8080
```

---

## Example Requests

### Register

```http
POST http://localhost:8080/api/auth/register
```

```json
{
  "fullName": "Ghusharib Najam",
  "email": "ghusharib@example.com",
  "password": "123456"
}
```

### Login

```http
POST http://localhost:8080/api/auth/login
```

```json
{
  "email": "ghusharib@example.com",
  "password": "123456"
}
```

Response:

```json
{
  "token": "jwt_token_here",
  "tokenType": "Bearer"
}
```

### Create Category

```http
POST http://localhost:8080/api/categories
```

```json
{
  "name": "Food",
  "type": "EXPENSE"
}
```

### Create Expense Transaction

```http
POST http://localhost:8080/api/transactions
```

```json
{
  "title": "Burger",
  "amount": 650,
  "type": "EXPENSE",
  "date": "2026-05-31",
  "note": "Lunch",
  "categoryId": 1
}
```

### Create Income Transaction

```http
POST http://localhost:8080/api/transactions
```

```json
{
  "title": "Freelance Payment",
  "amount": 20000,
  "type": "INCOME",
  "date": "2026-05-31",
  "note": "Client project",
  "categoryId": 2
}
```

### Create Budget

```http
POST http://localhost:8080/api/budgets
```

```json
{
  "limitAmount": 10000,
  "year": 2026,
  "month": 5,
  "categoryId": 1
}
```

### Get Monthly Summary

```http
GET http://localhost:8080/api/summary/monthly?year=2026&month=5
```

Example response:

```json
{
  "year": 2026,
  "month": 5,
  "totalIncome": 20000.00,
  "totalExpense": 650.00,
  "balance": 19350.00,
  "expenseByCategory": {
    "Food": 650.00
  }
}
```

---

## Screenshots

### Register User

<img width="1182" height="737" alt="Screenshot 2026-05-31 191318" src="https://github.com/user-attachments/assets/eef0aa32-0de1-4900-b134-cd151f32f318" />


### Login and JWT Token

<img width="1186" height="749" alt="Screenshot 2026-05-31 191528" src="https://github.com/user-attachments/assets/a856c483-fd27-46c9-83c4-84b640747884" />


### Create Category

Without Auth:

<img width="1178" height="632" alt="Screenshot 2026-05-31 191651" src="https://github.com/user-attachments/assets/578092d7-fb6c-40f8-b0ff-1f9d9d46bd76" />

With Auth:
<img width="1192" height="739" alt="Screenshot 2026-05-31 191744" src="https://github.com/user-attachments/assets/67a3d3d0-233e-49c0-8eab-b720ab7a08a9" />


### Create Transaction

<img width="1174" height="812" alt="Screenshot 2026-05-31 192002" src="https://github.com/user-attachments/assets/51153f74-821c-437c-a342-f6641c34c84c" />


### Filter Transactions by Category

<img width="1152" height="796" alt="Screenshot 2026-05-31 192307" src="https://github.com/user-attachments/assets/c9dadace-528e-4cf6-8ca1-467082ca534b" />


### Filter Transactions by Date

<img width="1190" height="893" alt="Screenshot 2026-05-31 192227" src="https://github.com/user-attachments/assets/ee49a97e-9d78-4fb6-b018-27ab3e3b59e2" />


### Create Budget

<img width="1182" height="796" alt="Screenshot 2026-05-31 192427" src="https://github.com/user-attachments/assets/25052c50-d31b-45c1-961b-a89c6811cafe" />


### Monthly Summary

<img width="1188" height="777" alt="Screenshot 2026-05-31 192506" src="https://github.com/user-attachments/assets/a778cb08-0287-424d-9fec-9b6920c16145" />


---

## Database Tables

The project creates the following main tables automatically using JPA:

* `app_users`
* `categories`
* `transactions`
* `budgets`

Main relationships:

* One user can have many categories.
* One user can have many transactions.
* One user can have many budgets.
* One category can have many transactions.
* One category can have many budgets.

---

## Security Highlights

* Passwords are hashed using BCrypt.
* JWT token is required for protected endpoints.
* Public endpoints are only register and login.
* Each user can access only their own categories, transactions, budgets, and summaries.
* API uses stateless authentication.

---

## What I Learned

While building this project, I practiced:

* Spring Boot REST API development
* MVC architecture
* Spring Security configuration
* JWT authentication
* PostgreSQL integration
* JPA relationships
* DTO-based request and response handling
* Service layer business logic
* User-specific data access
* Postman API testing

---

## Future Improvements

* Add Swagger/OpenAPI documentation
* Add refresh token support
* Add email verification
* Add password reset
* Add pagination for transactions
* Add budget exceeded warning API
* Add charts/dashboard frontend
* Add Docker support
* Add unit and integration tests

---

## Author

Ghusharib Najam

GitHub: https://github.com/ghusharibdev

---

## License

This project is open source and available under the MIT License.
