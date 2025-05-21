# Spring Boot Library Management System

## Overview
This project is a **Library Management System** built with **Spring Boot, MySQL, and JWT authentication**. It allows users to register, authenticate, manage books, and borrow/return books with **role-based access control**.

## Features
- **User Authentication & Authorization** using JWT
- **Admin & User Roles:**
  - Admins can **add, update, and delete books**
  - Users can **view and borrow books**
- **Book Management:**
  - View all books
  - Get book details by ID
  - Issue and return books
- **Database:** MySQL with JPA/Hibernate ORM

## Technologies Used
- **Spring Boot**
- **Spring MVC**
- **Spring Security & JWT** (Authentication)
- **MySQL** (Database)
- **JPA/Hibernate** (ORM)
- **Maven** (Dependency Management)

## Installation & Setup
### Prerequisites
- Java 17+
- MySQL installed and running
- Maven installed

### Steps to Run
1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```

2. **Configure MySQL database:**
   - Update `application.properties` with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/library_db
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     ```

3. **Build and run the application:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

4. **API Documentation:**
   - Refer to `API_DOCUMENTATION.md` for a detailed list of endpoints.

## API Endpoints
### Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/auth/registernormaluser` | Register a normal user |
| `POST` | `/auth/loginuser` | Login and get JWT token |
| `POST` | `/admin/registeradmin` | Register an admin user |

### Book Management
| Method | Endpoint | Role | Description |
|--------|---------|------|-------------|
| `GET` | `/books/getallbooks` | Any | View all books |
| `GET` | `/books/getbookbyid/{id}` | Any | Get book details by ID |
| `POST` | `/books/addbook` | Admin | Add a new book |
| `PUT` | `/books/updateBook/{id}` | Admin | Update book details |
| `DELETE` | `/books/deletebook/{id}` | Admin | Delete a book |

### Book Borrowing
| Method | Endpoint | Role | Description |
|--------|---------|------|-------------|
| `POST` | `/records/issuebook/{bookid}` | User | Borrow a book |
| `POST` | `/records/returnbook/{recordid}` | User | Return a book |

## Security & Authentication
- JWT-based authentication is used.
- Admin and User roles are assigned at registration.
- Secure endpoints require a **Bearer Token** in the `Authorization` header.



