# 📚 Library Management System

A desktop-based Library Management System built with **Java**, **Java Swing (GUI)**, and **MySQL**, following the **DAO (Data Access Object)** design pattern.

---

## 🖥️ Features

- 🔐 **User Login** – Secure authentication via MySQL database
- 📖 **Add Books** – Add new books with ID, title, author, and quantity
- 🎓 **Add Students** – Register students with ID, name, and department
- 📤 **Issue Book** – Issue a book to a student with real-time quantity update
- 📥 **Return Book** – Return a book and restore inventory count
- 📊 **View Books** – Display all books in an interactive JTable with refresh support

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java (JDK 8+) |
| GUI | Java Swing (JFrame, JTabbedPane, JTable) |
| Database | MySQL |
| Connectivity | JDBC (mysql-connector-j-9.4.0) |
| Pattern | DAO (Data Access Object) |
| Transactions | JDBC Transaction Management |

---

## 🗂️ Project Structure
```
library-management-system/
│
├── src/
│   ├── DBConnection.java       # MySQL database connection
│   ├── LibraryDAO.java         # All database operations (DAO layer)
│   ├── LibraryMain.java        # Console-based entry point
│   ├── LibraryGUI.java         # Main GUI window with tabbed panels
│   └── LoginGUI.java           # Login screen
│
├── lib/
│   └── mysql-connector-j-9.4.0.jar
│
└── config.properties           # DB credentials (not pushed to GitHub)
```

---

## ⚙️ How to Run

### Prerequisites
- Java JDK 8 or above
- MySQL Server running locally
- MySQL Connector JAR (included in `/lib`)

### Database Setup
```sql
CREATE DATABASE librarydb;
USE librarydb;

CREATE TABLE books (
    book_id INT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    quantity INT
);

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100),
    department VARCHAR(100)
);

CREATE TABLE users (
    username VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE issued_books (
    book_id INT,
    student_id INT,
    issue_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

INSERT INTO users VALUES ('admin', 'admin123');
```

### Configuration
Create a `config.properties` file:
```
db.url=jdbc:mysql://localhost:3306/librarydb
db.user=root
db.password=your_password_here
```

### Run
```bash
javac -cp .;lib/mysql-connector-j-9.4.0.jar src/*.java
java -cp .;lib/mysql-connector-j-9.4.0.jar LoginGUI
```

---

## 🔑 Key Concepts Implemented

- **DAO Pattern** – Separates database logic from UI logic
- **JDBC Transactions** – Atomic operations with rollback support
- **PreparedStatements** – Prevents SQL injection
- **Swing GUI** – Multi-tab interface using JTabbedPane
- **Secure Config** – Credentials excluded via `.gitignore`

---

## 👩‍💻 Author

**Ayushi Karn**  
B.Tech Computer Science and Engineering — Bharati Vidyapeeth (Deemed University) College of Engineering, Pune  

