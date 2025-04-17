package com.hexaware.dao;

import com.hexaware.entity.*;
import com.hexaware.exception.*;
import com.hexaware.util.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SISServiceImpl implements SISService {
    private Connection connection;

    // Constructor to initialize the database connection
    public SISServiceImpl() {
        try {
            this.connection = DBConnUtil.getConnection();
            initializeDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection: " + e.getMessage(), e);
        }
    }

    private void initializeDatabase() {
        String[] tables = {
            "CREATE TABLE IF NOT EXISTS Student (studentID INT PRIMARY KEY, firstName VARCHAR(50), lastName VARCHAR(50), dateOfBirth DATE, email VARCHAR(100), phoneNumber VARCHAR(15))",
            "CREATE TABLE IF NOT EXISTS Course (courseID INT PRIMARY KEY, courseName VARCHAR(100), courseCode VARCHAR(10), instructorName VARCHAR(100))",
            "CREATE TABLE IF NOT EXISTS Teacher (teacherID INT PRIMARY KEY, firstName VARCHAR(50), lastName VARCHAR(50), email VARCHAR(100))",
            "CREATE TABLE IF NOT EXISTS Enrollment (enrollmentID INT PRIMARY KEY AUTO_INCREMENT, studentID INT, courseID INT, enrollmentDate DATE, FOREIGN KEY (studentID) REFERENCES Student(studentID), FOREIGN KEY (courseID) REFERENCES Course(courseID))",
            "CREATE TABLE IF NOT EXISTS Payment (paymentID INT PRIMARY KEY AUTO_INCREMENT, studentID INT, amount DOUBLE, paymentDate DATE, FOREIGN KEY (studentID) REFERENCES Student(studentID))",
            "CREATE TABLE IF NOT EXISTS CourseTeacher (courseID INT, teacherID INT, FOREIGN KEY (courseID) REFERENCES Course(courseID), FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID))"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String table : tables) {
                stmt.execute(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema: " + e.getMessage(), e);
        }
    }

    @Override
    public void addStudent(Student student) throws InvalidStudentDataException {
        if (student == null || student.getFirstName() == null || student.getLastName() == null || student.getEmail() == null) {
            throw new InvalidStudentDataException("Invalid student data");
        }
        String query = "INSERT INTO Student (studentID, firstName, lastName, dateOfBirth, email, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setDate(4, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidStudentDataException("Error adding student: " + e.getMessage());
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException {
        if (student == null || student.getFirstName() == null || student.getLastName() == null || student.getEmail() == null) {
            throw new InvalidStudentDataException("Invalid student data");
        }
        String query = "UPDATE Student SET firstName = ?, lastName = ?, dateOfBirth = ?, email = ?, phoneNumber = ? WHERE studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setDate(3, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhoneNumber());
            pstmt.setInt(6, student.getStudentID());
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new StudentNotFoundException("Student not found with ID: " + student.getStudentID());
            }
        } catch (SQLException e) {
            throw new InvalidStudentDataException("Error updating student: " + e.getMessage());
        }
    }

    @Override
    public Student getStudent(int studentID) throws StudentNotFoundException {
        String query = "SELECT * FROM Student WHERE studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("studentID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getDate("dateOfBirth") != null ? rs.getDate("dateOfBirth").toLocalDate() : null,
                    rs.getString("email"),
                    rs.getString("phoneNumber")
                );
            }
            throw new StudentNotFoundException("Student not found with ID: " + studentID);
        } catch (SQLException e) {
            throw new StudentNotFoundException("Error retrieving student: " + e.getMessage());
        }
    }

    @Override
    public List<Course> getEnrolledCourses(int studentID) throws StudentNotFoundException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.* FROM Course c JOIN Enrollment e ON c.courseID = e.courseID WHERE e.studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("courseID"),
                    rs.getString("courseName"),
                    rs.getString("courseCode"),
                    rs.getString("instructorName")
                ));
            }
            if (courses.isEmpty()) {
                throw new StudentNotFoundException("No courses found for student ID: " + studentID);
            }
            return courses;
        } catch (SQLException e) {
            throw new StudentNotFoundException("Error retrieving enrolled courses: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentHistory(int studentID) throws StudentNotFoundException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payment WHERE studentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("paymentID"),
                    getStudent(studentID),
                    rs.getDouble("amount"),
                    rs.getDate("paymentDate") != null ? rs.getDate("paymentDate").toLocalDate() : null
                ));
            }
            if (payments.isEmpty()) {
                throw new StudentNotFoundException("No payment history found for student ID: " + studentID);
            }
            return payments;
        } catch (SQLException e) {
            throw new StudentNotFoundException("Error retrieving payment history: " + e.getMessage());
        }
    }

    @Override
    public void addCourse(Course course) throws InvalidCourseDataException {
        if (course == null || course.getCourseName() == null || course.getCourseCode() == null) {
            throw new InvalidCourseDataException("Invalid course data");
        }
        String query = "INSERT INTO Course (courseID, courseName, courseCode, instructorName) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, course.getCourseID());
            pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getCourseCode());
            pstmt.setString(4, course.getInstructorName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidCourseDataException("Error adding course: " + e.getMessage());
        }
    }

    @Override
    public void updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException {
        if (course == null || course.getCourseName() == null || course.getCourseCode() == null) {
            throw new InvalidCourseDataException("Invalid course data");
        }
        String query = "UPDATE Course SET courseName = ?, courseCode = ?, instructorName = ? WHERE courseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setString(3, course.getInstructorName());
            pstmt.setInt(4, course.getCourseID());
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new CourseNotFoundException("Course not found with ID: " + course.getCourseID());
            }
        } catch (SQLException e) {
            throw new InvalidCourseDataException("Error updating course: " + e.getMessage());
        }
    }

    @Override
    public Course getCourse(int courseID) throws CourseNotFoundException {
        String query = "SELECT * FROM Course WHERE courseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("courseID"),
                    rs.getString("courseName"),
                    rs.getString("courseCode"),
                    rs.getString("instructorName")
                );
            }
            throw new CourseNotFoundException("Course not found with ID: " + courseID);
        } catch (SQLException e) {
            throw new CourseNotFoundException("Error retrieving course: " + e.getMessage());
        }
    }

    @Override
    public List<Enrollment> getCourseEnrollments(int courseID) throws CourseNotFoundException, StudentNotFoundException {
        List<Enrollment> enrollments = new ArrayList<>();
        String query = "SELECT * FROM Enrollment WHERE courseID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                enrollments.add(new Enrollment(
                    rs.getInt("enrollmentID"),
                    getStudent(rs.getInt("studentID")),
                    getCourse(courseID),
                    rs.getDate("enrollmentDate") != null ? rs.getDate("enrollmentDate").toLocalDate() : null
                ));
            }
            if (enrollments.isEmpty()) {
                throw new CourseNotFoundException("No enrollments found for course ID: " + courseID);
            }
            return enrollments;
        } catch (SQLException e) {
            throw new CourseNotFoundException("Error retrieving enrollments: " + e.getMessage());
        }
    }

    @Override
    public void enrollStudentInCourse(int studentID, int courseID, LocalDate enrollmentDate)
            throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InsufficientFundsException {
        String checkQuery = "SELECT COUNT(*) FROM Enrollment WHERE studentID = ? AND courseID = ?";
        String insertQuery = "INSERT INTO Enrollment (studentID, courseID, enrollmentDate) VALUES (?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, studentID);
                checkStmt.setInt(2, courseID);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new DuplicateEnrollmentException("Student already enrolled in course");
                }
            }
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, studentID);
                insertStmt.setInt(2, courseID);
                insertStmt.setDate(3, enrollmentDate != null ? Date.valueOf(enrollmentDate) : null);
                insertStmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error enrolling student: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void addTeacher(Teacher teacher) throws InvalidTeacherDataException {
        if (teacher == null || teacher.getFirstName() == null || teacher.getEmail() == null) {
            throw new InvalidTeacherDataException("Invalid teacher data");
        }
        String query = "INSERT INTO Teacher (teacherID, firstName, lastName, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, teacher.getTeacherID());
            pstmt.setString(2, teacher.getFirstName());
            pstmt.setString(3, teacher.getLastName());
            pstmt.setString(4, teacher.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidTeacherDataException("Error adding teacher: " + e.getMessage());
        }
    }

    @Override
    public void assignTeacherToCourse(int teacherID, int courseID)
            throws TeacherNotFoundException, CourseNotFoundException {
        String query = "INSERT INTO CourseTeacher (courseID, teacherID) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, courseID);
                pstmt.setInt(2, teacherID);
                pstmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error assigning teacher: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Teacher getTeacher(int teacherID) throws TeacherNotFoundException {
        String query = "SELECT * FROM Teacher WHERE teacherID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, teacherID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Teacher(
                    rs.getInt("teacherID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email")
                );
            }
            throw new TeacherNotFoundException("Teacher not found with ID: " + teacherID);
        } catch (SQLException e) {
            throw new TeacherNotFoundException("Error retrieving teacher: " + e.getMessage());
        }
    }

    @Override
    public List<Course> getAssignedCourses(int teacherID) throws TeacherNotFoundException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.* FROM Course c JOIN CourseTeacher ct ON c.courseID = ct.courseID WHERE ct.teacherID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, teacherID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("courseID"),
                    rs.getString("courseName"),
                    rs.getString("courseCode"),
                    rs.getString("instructorName")
                ));
            }
            if (courses.isEmpty()) {
                throw new TeacherNotFoundException("No courses assigned to teacher ID: " + teacherID);
            }
            return courses;
        } catch (SQLException e) {
            throw new TeacherNotFoundException("Error retrieving assigned courses: " + e.getMessage());
        }
    }

    @Override
    public void recordPayment(int studentID, double amount, LocalDate paymentDate)
            throws StudentNotFoundException, PaymentValidationException {
        if (amount <= 0) {
            throw new PaymentValidationException("Invalid payment amount");
        }
        String query = "INSERT INTO Payment (studentID, amount, paymentDate) VALUES (?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, studentID);
                pstmt.setDouble(2, amount);
                pstmt.setDate(3, paymentDate != null ? Date.valueOf(paymentDate) : null);
                pstmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Rollback failed: " + ex.getMessage(), ex);
            }
            throw new RuntimeException("Error recording payment: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to reset auto-commit: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public String generateEnrollmentReport(int courseID) throws CourseNotFoundException, StudentNotFoundException {
        StringBuilder report = new StringBuilder("Enrollment Report for Course ID: " + courseID + "\n");
        List<Enrollment> enrollments = getCourseEnrollments(courseID);
        for (Enrollment e : enrollments) {
            report.append("Student: ")
                  .append(e.getStudent().getFirstName())
                  .append(" ")
                  .append(e.getStudent().getLastName())
                  .append(", Enrollment Date: ")
                  .append(e.getEnrollmentDate())
                  .append("\n");
        }
        return report.toString();
    }

    @Override
    public String generatePaymentReport(int studentID) throws StudentNotFoundException {
        StringBuilder report = new StringBuilder("Payment Report for Student ID: " + studentID + "\n");
        List<Payment> payments = getPaymentHistory(studentID);
        for (Payment p : payments) {
            report.append("Amount: ")
                  .append(p.getAmount())
                  .append(", Date: ")
                  .append(p.getPaymentDate())
                  .append("\n");
        }
        return report.toString();
    }

    @Override
    public List<Object> executeDynamicQuery(String query, Object... params) {
        List<Object> results = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing dynamic query: " + e.getMessage(), e);
        }
        return results;
    }

    // Close connection when done
    public void close() {
        try {
            DBConnUtil.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database connection: " + e.getMessage(), e);
        }
    }
}