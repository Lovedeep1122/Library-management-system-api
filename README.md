# 📚 Library Management System API

A robust RESTful backend API built with **Java** and **Spring Boot** to manage library operations, including member registration, inventory tracking, and book borrowing/returning logic.

## 🚀 Technologies Used
*   **Language:** Java 17
*   **Framework:** Spring Boot (Spring Web, Spring Data JPA)
*   **Database:** MySQL
*   **Tools:** Maven, Postman, Lombok

## ⚙️ Key Features & Architecture
*   **Relational Database Mapping:** Designed a normalized MySQL database utilizing `@OneToMany` and `@ManyToOne` JPA relationships to link `Members`, `Books`, and `BorrowingRecords`.
*   **Transactional Integrity:** Implemented `@Transactional` service layers to ensure data consistency when updating book inventory and borrowing records simultaneously.
*   **Data Transfer Objects (DTOs):** Engineered DTOs (e.g., `BorrowingRecordResponseDTO`) to cleanly format JSON API responses and eliminate infinite recursion/circular reference bugs during serialization.
*   **Global Exception Handling:** Utilized `@ControllerAdvice` to intercept `RuntimeExceptions` and validation errors, returning standardized, frontend-friendly JSON error objects.
*   **Input Validation:** Enforced data integrity at the controller level using `spring-boot-starter-validation` (`@NotBlank`, `@Email`, `@PastOrPresent`).

## 📡 API Endpoints

### Books
*   `POST /books` - Add a new book to the inventory
*   `GET /books` - Retrieve all books and current available copies

### Members
*   `POST /members` - Register a new library member (includes email validation)
*   `GET /members` - Retrieve all registered members

### Borrowing Operations
*   `POST /api/borrow/{memberId}/{bookId}` - Borrow a book (decreases inventory, sets status to "BORROWED")
*   `PUT /api/borrow/return/{memberId}/{bookId}` - Return a book (increases inventory, updates return date)
*   `GET /api/borrow` - View complete borrowing history

## 💡 Technical Challenges Solved
**The Circular Reference Problem:** Initially, querying borrowing records resulted in a `StackOverflowError` due to bi-directional JPA relationships between Books and Records. 
*   *Solution:* Designed and mapped Data Transfer Objects (DTOs) to decouple the database entities from the JSON serialization process, resulting in clean, flat API responses.

## 🛠️ How to Run Locally
1. Clone the repository.
2. Update the `application.properties` file with your local MySQL credentials.
3. Run the application via your IDE or using `mvn spring-boot:run`.
4. The API will be available at `http://localhost:8080`.
