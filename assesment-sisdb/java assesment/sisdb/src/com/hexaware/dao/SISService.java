package com.hexaware.dao;



import com.hexaware.entity.*;
import com.hexaware.exception.*;

import java.time.LocalDate;
import java.util.List;

public interface SISService {
    // Student Operations
    void addStudent(Student student) throws InvalidStudentDataException;
    void updateStudent(Student student) throws StudentNotFoundException, InvalidStudentDataException;
    Student getStudent(int studentID) throws StudentNotFoundException;
    List<Course> getEnrolledCourses(int studentID) throws StudentNotFoundException;
    List<Payment> getPaymentHistory(int studentID) throws StudentNotFoundException;

    // Course Operations
    void addCourse(Course course) throws InvalidCourseDataException;
    void updateCourse(Course course) throws CourseNotFoundException, InvalidCourseDataException;
    Course getCourse(int courseID) throws CourseNotFoundException;
    List<Enrollment> getCourseEnrollments(int courseID) throws CourseNotFoundException, StudentNotFoundException;

    // Enrollment Operations
    void enrollStudentInCourse(int studentID, int courseID, LocalDate enrollmentDate)
            throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InsufficientFundsException;

    // Teacher Operations
    void addTeacher(Teacher teacher) throws InvalidTeacherDataException;
    void assignTeacherToCourse(int teacherID, int courseID)
            throws TeacherNotFoundException, CourseNotFoundException;
    Teacher getTeacher(int teacherID) throws TeacherNotFoundException;
    List<Course> getAssignedCourses(int teacherID) throws TeacherNotFoundException;

    // Payment Operations
    void recordPayment(int studentID, double amount, LocalDate paymentDate)
            throws StudentNotFoundException, PaymentValidationException;

    // Reports
    String generateEnrollmentReport(int courseID) throws CourseNotFoundException, StudentNotFoundException;
    String generatePaymentReport(int studentID) throws StudentNotFoundException;

    // Database Query
    List<Object> executeDynamicQuery(String query, Object... params);
}