# BookStore Management REST API

A comprehensive REST API for bookstore management built with Spring Boot, MySQL, JWT, and Spring Security.

## Features

- **User Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control (USER, ADMIN)
  - Signup and login endpoints
  
- **Book Management**
  - Create, Read, Update, Delete (CRUD) operations
  - Admin-only access for creating, updating, and deleting books
  - All authenticated users can view books
  
- **Security**
  - Password encryption using BCrypt
  - JWT token generation and validation
  - Stateless session management
  - Protected API endpoints

## Technologies Used

- **Spring Boot 2.7.14** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **MySQL 8** - Database
- **JWT (jjwt 0.11.5)** - Token-based authentication
- **Lombok** - Reduces boilerplate code
- **Maven** - Dependency management

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/Shivamyadav95/BookStoreManagement.git
cd BookStoreManagement
```

### 2. Configure MySQL Database

Create a MySQL database:

```sql
CREATE DATABASE bookstore_db;
```

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 3. Build and Run the application

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Default Admin User

A default admin user is automatically created on startup:
- **Username**: `admin`
- **Password**: `admin123`

## API Endpoints

### Authentication Endpoints

#### Register a new user
```http
POST /api/auth/signup
Content-Type: application/json

{
  "username": "john",
  "email": "john@example.com",
  "password": "password123",
  "roles": ["user"]
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "john",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

### Book Management Endpoints

All book endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Get all books (USER/ADMIN)
```http
GET /api/books
```

#### Get book by ID (USER/ADMIN)
```http
GET /api/books/{id}
```

#### Create a new book (ADMIN only)
```http
POST /api/books
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "price": 42.99,
  "quantity": 10,
  "description": "A Handbook of Agile Software Craftsmanship"
}
```

#### Update a book (ADMIN only)
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "price": 39.99,
  "quantity": 15,
  "description": "Updated description"
}
```

#### Delete a book (ADMIN only)
```http
DELETE /api/books/{id}
```

## Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique)
- `email`
- `password` (Encrypted)

### Roles Table
- `id` (Primary Key)
- `name` (ROLE_USER, ROLE_ADMIN)

### Books Table
- `id` (Primary Key)
- `title`
- `author`
- `isbn` (Unique)
- `price`
- `quantity`
- `description`
- `created_at`
- `updated_at`

### User_Roles Table (Junction Table)
- `user_id` (Foreign Key)
- `role_id` (Foreign Key)

## Security Configuration

- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours (configurable in `application.properties`)
- Stateless session management
- CORS enabled for all origins (can be customized)

## Error Handling

The API returns appropriate HTTP status codes and error messages:
- `200 OK` - Successful GET, PUT requests
- `201 Created` - Successful POST requests
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Missing or invalid authentication
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server-side errors

## Testing with cURL

### Register a user
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"test123","roles":["user"]}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"test123"}'
```

### Get all books
```bash
curl -X GET http://localhost:8080/api/books \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create a book (admin only)
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"title":"Test Book","author":"Test Author","isbn":"123-456-789","price":29.99,"quantity":5,"description":"Test description"}'
```

## Project Structure

```
src/main/java/com/bookstore/
├── controller/          # REST Controllers
├── dto/                 # Data Transfer Objects
├── exception/           # Exception classes and handlers
├── model/               # Entity models
├── repository/          # JPA repositories
├── security/            # Security configuration and JWT utilities
├── service/             # Business logic services
├── BookStoreManagementApplication.java
└── DataInitializer.java # Initializes roles and admin user
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is open source and available under the MIT License.