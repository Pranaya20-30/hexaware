package com.hexaware.entity;



import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseID;
    private String courseName;
    private String courseCode;
    private String instructorName;
    private Teacher assignedTeacher;
    private List<Enrollment> enrollments;

    public Course(int courseID, String courseName, String courseCode, String instructorName) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructorName = instructorName;
        this.enrollments = new ArrayList<>();
    }

    // Getters and Setters
    public int getCourseID() { return courseID; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher assignedTeacher) { this.assignedTeacher = assignedTeacher; }
    public List<Enrollment> getEnrollments() { return enrollments; }
}