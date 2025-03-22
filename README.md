Assesment 2 
Student information system

-- Task 1: Database and Table Creation

CREATE DATABASE IF NOT EXISTS sisdb;
USE sisdb;

-- Creating Students Table
CREATE TABLE IF NOT EXISTS students (
    student_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL UNIQUE
);

-- Creating Teachers Table
CREATE TABLE IF NOT EXISTS teachers (
    teacher_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE
);

-- Creating Courses Table
CREATE TABLE IF NOT EXISTS courses (
    course_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(50) NOT NULL,
    credits INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id) ON DELETE CASCADE
);

-- Creating Enrollments Table
CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Creating Payments Table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Task 2: Data Insertion
-- Inserting Sample Data into Students Table
INSERT INTO students (first_name, last_name, date_of_birth, email, phone_number) VALUES
('John', 'Doe', '1995-08-15', 'john.doe@example.com', '1234567890');

-- Inserting Enrollment
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 4, CURDATE());

-- Updating Teacher Email
UPDATE teachers
SET email = 'johndoe90876@gmail.com'
WHERE teacher_id = 2;

-- Deleting Enrollment Record
DELETE FROM enrollments
WHERE student_id = 1 AND course_id = 4;

-- Updating Teacher for a Course
UPDATE courses
SET teacher_id = 3
WHERE course_id = 5;

-- Deleting a Student Record
DELETE FROM students
WHERE student_id = 11;

-- Updating Payment Amount
UPDATE payments
SET amount = 600
WHERE payment_id = 7;

-- Task 3: Queries with Subqueries

-- Total Payments per Student
SELECT student_id, student_name, total_payment 
FROM (
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT SUM(p.amount) FROM payments p WHERE p.student_id = s.student_id) AS total_payment
    FROM students s
) AS student_total;

-- Count of Students Enrolled in Each Course
SELECT course_id, course_name, student_count 
FROM (
    SELECT c.course_id, 
           c.course_name, 
           (SELECT COUNT(e.student_id) FROM enrollments e WHERE e.course_id = c.course_id) AS student_count
    FROM courses c
) AS course_enrollment;

-- Students with More Than One Payment
SELECT student_id, student_name 
FROM (
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT COUNT(*) FROM payments p WHERE p.student_id = s.student_id) AS payment_count
    FROM students s
) AS student_payments 
WHERE payment_count > 1;

-- Average Payment Per Student
SELECT student_id, student_name, avg_payment 
FROM (
    SELECT s.student_id, 
           CONCAT(s.first_name, ' ', s.last_name) AS student_name, 
           (SELECT AVG(p.amount) FROM payments p WHERE p.student_id = s.student_id) AS avg_payment
    FROM students s
) AS student_avg;

-- Courses Without Enrollments
SELECT c.course_id, c.course_name 
FROM courses c
LEFT JOIN (
    SELECT DISTINCT course_id FROM enrollments) as e ON c.course_id = e.course_id
WHERE e.course_id IS NULL;

-- Teachers Without Courses
SELECT t.teacher_id, CONCAT(t.first_name, ' ', t.last_name) AS teacher_name
FROM teachers t
LEFT JOIN (
    SELECT DISTINCT teacher_id FROM courses) AS c ON t.teacher_id = c.teacher_id
WHERE c.teacher_id IS NULL;

ER diagram
![image](https://github.com/user-attachments/assets/2c345159-4e35-4ae0-8809-6f9de7c3b9fb)

