# 🎓 FMTALI Learning Management System (LMS)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Supabase](https://img.shields.io/badge/Supabase-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)

A desktop-based Learning Management System built with **Java Swing** and **PostgreSQL (Supabase cloud)**, designed to manage students, courses, attendance, and academic performance.

---

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Running the App](#running-the-app)
- [User Roles](#user-roles)
- [Technologies Used](#technologies-used)
- [Roadmap](#roadmap)

---

## 📖 About

FMTALI LMS is a Java desktop application developed as part of a software development learning journey. It provides a role-based system for managing student records, tracking academic performance, and monitoring attendance — all backed by a cloud PostgreSQL database hosted on Supabase.

---

## ✨ Features

- 🔐 **Role-based login** — Admin, Facilitator, and Student access levels
- 👨‍🎓 **Student management** — Add, update, delete, and view student records
- 📚 **Course management** — Assign students to courses (Java, Python, Web Development, etc.)
- 📊 **Mark tracking** — Projects (best 2 of 3), Tests (best 5 of 7 for Java), and Exam marks
- 🏆 **Auto grading** — Final mark calculated as Project 20% + Test 30% + Exam 50%
- 🔓 **Exam unlock system** — Students must achieve ≥50% combined mark to write exam
- 📅 **Attendance tracking** — Sign-in/sign-out with location status
- 🔒 **Password security** — SHA-256 hashed passwords with strength validation
- ☁️ **Cloud database** — Data persists on Supabase (accessible from any device)
- 🔄 **First login password change** — Users must change default password on first login

---

## 📁 Project Structure

```
final Lesson 2/
├── lib/
│   └── postgresql-42.7.11.jar       # JDBC driver
├── src/
│   └── com/fmtali/lms/
│       ├── Main.java                # Entry point
│       ├── TestConnection.java      # DB connection test
│       ├── model/
│       │   ├── Student.java         # Student entity
│       │   ├── Course.java          # Course constants
│       │   └── User.java            # User entity
│       ├── service/
│       │   ├── StudentService.java  # Student CRUD (database)
│       │   ├── AuthService.java     # Login & authentication
│       │   └── AttendanceService.java
│       ├── ui/
│       │   ├── LoginPanel.java      # Login screen
│       │   ├── DashboardPanel.java  # Main dashboard
│       │   ├── FormPanel.java       # Student form
│       │   ├── TablePanel.java      # Student table view
│       │   └── ChangePasswordDialog.java
│       └── util/
│           ├── DatabaseConnection.java  # Supabase connection
│           ├── PasswordUtil.java        # Hashing & validation
│           └── ValidationUtil.java      # Form validation
```

---

## 🗄️ Database Schema

Hosted on **Supabase (PostgreSQL)**

```sql
-- 4 Tables
courses      (id, course_name)
users        (id, user_id, full_name, password_hash, role, first_login, created_at)
students     (id, student_id, full_name, email, course_id, enroll_date, status,
              project1-3, test1-7, exam, exam_unlocked, created_at)
attendance   (id, person_id, person_name, role, sign_in_time, sign_out_time,
              latitude, longitude, location_status, status, created_at)
```

---

## ✅ Prerequisites

- Java JDK 11 or higher
- PostgreSQL JDBC Driver (`postgresql-42.7.11.jar`)
- Supabase account (free tier)
- Internet connection (for cloud database access)

---

## ⚙️ Setup & Installation

### 1. Clone the repository
```bash
git clone https://github.com/Zamangema/FMTALI-LMS
cd fmtali-lms
```

### 2. Add the JDBC Driver
Download [postgresql-42.7.11.jar](https://jdbc.postgresql.org/download/) and place it in the `lib/` folder.

### 3. Configure Database Connection
Open `src/com/fmtali/lms/util/DatabaseConnection.java` and update:

```java
private static final String URL      = "jdbc:postgresql://your-supabase-host:5432/postgres";
private static final String USER     = "postgres.your-project-id";
private static final String PASSWORD = "your-supabase-password";
```

### 4. Set up the Database
Run the following SQL in your Supabase SQL Editor:

```sql
CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(255),
    password_hash VARCHAR(255),
    role VARCHAR(50),
    first_login BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    student_id VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    course_id INTEGER REFERENCES courses(id),
    enroll_date DATE,
    status VARCHAR(50),
    project1 NUMERIC(5,2), project2 NUMERIC(5,2), project3 NUMERIC(5,2),
    test1 NUMERIC(5,2), test2 NUMERIC(5,2), test3 NUMERIC(5,2),
    test4 NUMERIC(5,2), test5 NUMERIC(5,2), test6 NUMERIC(5,2),
    test7 NUMERIC(5,2), exam NUMERIC(5,2),
    exam_unlocked BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    person_id VARCHAR(100),
    person_name VARCHAR(255),
    role VARCHAR(50),
    sign_in_time TIMESTAMP,
    sign_out_time TIMESTAMP,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    location_status VARCHAR(100),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed courses
INSERT INTO courses (course_name) VALUES ('Java'), ('Python'), ('Web Development'), ('Database');
```

---

## ▶️ Running the App

### Compile
```cmd
javac -cp ".;lib/postgresql-42.7.11.jar" -d . src/com/fmtali/lms/util/*.java src/com/fmtali/lms/model/*.java src/com/fmtali/lms/service/*.java src/com/fmtali/lms/ui/*.java src/com/fmtali/lms/Main.java
```

### Run
```cmd
java -cp ".;lib/postgresql-42.7.11.jar" com.fmtali.lms.Main
```

> On Mac/Linux replace `;` with `:` in the classpath

---

## 👥 User Roles

| Role | Permissions |
|------|-------------|
| **Admin** | Full access — add, update, delete students, manage users |
| **Facilitator** | Update marks, change student status (Suspended/Dropped) |
| **Student** | View own profile and academic record only |

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 11+ | Core application language |
| Java Swing | Desktop UI framework |
| PostgreSQL | Relational database |
| Supabase | Cloud database hosting |
| JDBC | Java database connectivity |
| SHA-256 | Password hashing |

---

## 🗺️ Roadmap

- [ ] Student profile photo upload
- [ ] Spring Boot REST API backend
- [ ] Web app version (React/Angular frontend)
- [ ] Mobile app version
- [ ] Email notifications for exam unlock
- [ ] PDF report generation
- [ ] GitHub Actions CI/CD pipeline

---

## 👨‍💻 Developer

**Bonginkosi Zamangema Nene<zamakhatha@gmail.com>** — FMTALI Software Development Student

> Built as part of the FMTALI Java Development Programme 🎓

---

## 📄 License

This project is for educational purposes as part of the FMTALI curriculum.
