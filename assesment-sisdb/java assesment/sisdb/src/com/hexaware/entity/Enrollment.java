package com.hexaware.entity;



import java.time.LocalDate;

public class Enrollment {
    private int enrollmentID;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;

    public Enrollment(int enrollmentID, Student student, Course course, LocalDate enrollmentDate) {
        this.enrollmentID = enrollmentID;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters
    public int getEnrollmentID() { return enrollmentID; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
}
    
   

    
