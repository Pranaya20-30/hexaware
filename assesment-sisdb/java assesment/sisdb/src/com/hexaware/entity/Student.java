package com.hexaware.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int studentID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private List<Enrollment> enrollments;
    private List<Payment> payments;

    public Student(int studentID, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    // Getters and Setters
    public int getStudentID() { return studentID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public List<Payment> getPayments() { return payments; }
}
