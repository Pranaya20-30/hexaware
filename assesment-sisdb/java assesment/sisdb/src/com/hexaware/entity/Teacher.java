package com.hexaware.entity;



import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private int teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> assignedCourses;

    public Teacher(int teacherID, String firstName, String lastName, String email) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.assignedCourses = new ArrayList<>();
    }

    // Getters and Setters
    public int getTeacherID() { return teacherID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Course> getAssignedCourses() { return assignedCourses; }
}