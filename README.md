# Online Library Web Application

This is a full-stack web application developed to simulate an online library system. Users can browse a collection of books, search for specific titles, borrow books, and manage their borrowed items, along with user authentication and profile management.

Note on Data Initialization: A CSV file is used for book data. It is a requirement to first run the endpoint /import-books for the books to be visible in the system.

Future Enhancements: Currently, security for authentication has not been implemented. This is a planned enhancement for the future, acknowledging its critical importance.
---

## Features

* **User Authentication:** User registration, login, and logout functionalities.
* **User Profile Management:** Users can update their personal information (e.g., username).
* **Book Catalog:** Display a comprehensive list of books with details like Title, Author, Category, Publication Year, Ratings, and Description.
* **Categorized Browse:** Books are displayed in organized categories for easy navigation.
* **Search Functionality:** Efficient search to find books by keywords.
* **Borrowing System:**
    * Users can borrow books with a click of a button.
    * A maximum limit of 5 borrowed books per user is enforced.
    * Books cannot be borrowed if already borrowed by another user.
* **"My Borrowed Books" Section:** A dedicated area for users to view and manage their currently borrowed books.
* **Return Functionality:** Users can return books, making them available again for others.

---

## Technologies Used

* **Back-End:**
    * Java
    * Spring Boot (Framework)
    * RESTful API design
* **Front-End:**
    * React (with Vite for fast development)
    * CSS (for styling)
* **Database:**
  * MySQL 

---

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or higher
* Node.js and npm (or Yarn)
* MySQL 

---

## Contribution

This project was developed independently as a personal academic project.
