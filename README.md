# Expense Tracking API

A  REST API for tracking and managing personal or business expenses built with Spring Boot 3.4.5.

## Overview

The Expense Tracking API provides a comprehensive solution for expense management with full JWT authentication. It allows users to track expenses, categorize them, set budgets, and generate reports. The application includes user login/registration, category organization, expense tracking, and budget monitoring features.

## Features

- **Expense Management**: Create, retrieve, update, and delete expense records
- **Category Management**: Organize expenses with customizable categories
- **User Authentication**: Secure API endpoints with JWT token-based authentication
- **Budget Management**: Set and monitor monthly budgets (overall and by category)
- **Reporting**: Generate expense summaries and reports by month or category
- **Data Validation**: Comprehensive input validation with informative error messages
- **Database Migrations**: Automated schema management using Flyway
- **Error Handling**: Global exception handling with appropriate HTTP status codes

## Tech Stack

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Security** with JWT authentication
- **Spring Data JPA** for database operations
- **MySQL** as the database
- **Lombok** for reducing boilerplate code
- **Flyway** for database migrations
- **Maven** for dependency management and build automation

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+

## Getting Started

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/expense-tracking-api.git
cd expense-tracking-api
```

2. Configure your database connection in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword
```

3. Build the application:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The server will start at `http://localhost:8080`.

## Database Setup

The application uses Flyway for database migrations. On startup, it will automatically:

1. Create the necessary tables (users, categories, expenses, budgets)
2. Populate default expense categories

You can modify the initial migration script at `src/main/resources/db/migration/V1__Initial_Schema.sql`.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate and get JWT token |

### Expenses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/expenses` | Get all expenses for current user |
| GET | `/api/expenses/{id}` | Get expense by ID |
| POST | `/api/expenses` | Create a new expense |
| PUT | `/api/expenses/{id}` | Update an existing expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| GET | `/api/expenses/summary` | Get expense summary for current user |
| GET | `/api/expenses/summary/month` | Get monthly expense summary |
| GET | `/api/expenses/by-month` | Get expenses by month |
| GET | `/api/expenses/by-category/{categoryId}` | Get expenses by category |

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{id}` | Get category by ID |
| POST | `/api/categories` | Create a new category |
| PUT | `/api/categories/{id}` | Update an existing category |
| DELETE | `/api/categories/{id}` | Delete a category |

### Budgets

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/budgets` | Set a budget for a month (overall or by category) |

## Request & Response Examples

### Authentication

#### Register a New User

```
POST /api/auth/register
```

Request body:
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "johndoe"
}
```

#### Login

```
POST /api/auth/login
```

Request body:
```json
{
  "username": "johndoe",
  "password": "securepassword"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "johndoe"
}
```

### Expenses

#### Create an Expense

```
POST /api/expenses
```

Request body:
```json
{
  "description": "Grocery shopping",
  "amount": 75.50,
  "categoryId": 1
}
```

Response:
```json
{
  "id": 1,
  "description": "Grocery shopping",
  "amount": 75.50,
  "date": "2025-05-20T14:30:45",
  "categoryId": 1,
  "categoryName": "Food"
}
```

#### Get Monthly Summary

```
GET /api/expenses/summary/month?year=2025&month=5
```

Response:
```json
{
  "totalAmount": 1250.75,
  "amountByCategory": {
    "Food": 320.50,
    "Housing": 750.00,
    "Transportation": 180.25
  },
  "totalCount": 15,
  "budget": 2000.00,
  "remainingBudget": 749.25,
  "budgetExceeded": false
}
```

### Budgets

#### Set a Monthly Budget

```
POST /api/budgets
```

Request body:
```json
{
  "yearMonth": "2025-05",
  "amount": 2000.00
}
```

Response:
```json
{
  "id": 1,
  "yearMonth": "2025-05",
  "amount": 2000.00
}
```

## Security

All endpoints except for authentication (`/api/auth/**`) require a valid JWT token in the Authorization header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Error Handling

The API provides comprehensive error handling with appropriate HTTP status codes:

| Status Code | Description |
|-------------|-------------|
| 400 | Bad Request - Invalid input data |
| 401 | Unauthorized - Missing or invalid authentication |
| 403 | Forbidden - User doesn't have access to the resource |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server-side error |

Error responses follow this format:

```json
{
  "status": 404,
  "message": "Expense not found with id: 123"
}
```

For validation errors, the response contains field-specific error messages:

```json
{
  "amount": "Amount must be positive",
  "description": "Description is required"
}
```

## Configuration

Key application properties that can be configured in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=root
spring.datasource.password=yourpassword

# JWT Configuration
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000  # 24 hours in milliseconds

# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

## Development

### Project Structure

```
src/main/java/com/expenseTracking/application/
├── config/                 # Application configuration
├── controller/             # REST controllers
├── dto/                    # Data Transfer Objects
├── exception/              # Exception handling
├── model/                  # JPA entities
├── repository/             # Spring Data repositories
├── request/                # Request models
├── response/               # Response models
├── security/               # Security configuration
├── service/                # Business logic
└── ExpenseTrackingApplication.java  # Main application class
```

### Building for Production

```bash
./mvnw clean package
```

This will create a JAR file in the `target/` directory that can be deployed to any environment with Java 17+.

 
