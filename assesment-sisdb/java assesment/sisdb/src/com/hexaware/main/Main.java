package com.hexaware.main;



import com.hexaware.dao.*;

import com.hexaware.entity.*;
import com.hexaware.exception.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static SISService sisService = new SISServiceImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> addCourse();
                    case 3 -> addTeacher();
                    case 4 -> enrollStudent();
                    case 5 -> assignTeacher();
                    case 6 -> recordPayment();
                    case 7 -> generateEnrollmentReport();
                    case 8 -> generatePaymentReport();
                    case 9 -> executeDynamicQuery();
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nStudent Information System");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Add Teacher");
        System.out.println("4. Enroll Student in Course");
        System.out.println("5. Assign Teacher to Course");
        System.out.println("6. Record Payment");
        System.out.println("7. Generate Enrollment Report");
        System.out.println("8. Generate Payment Report");
        System.out.println("9. Execute Dynamic Query");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addStudent() throws InvalidStudentDataException {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        Student student = new Student(id, firstName, lastName, dob, email, phone);
        sisService.addStudent(student);
        System.out.println("Student added successfully");
    }

    private static void addCourse() throws InvalidCourseDataException {
        System.out.print("Enter Course ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Instructor Name: ");
        String instructor = scanner.nextLine();

        Course course = new Course(id, name, code, instructor);
        sisService.addCourse(course);
        System.out.println("Course added successfully");
    }

    private static void addTeacher() throws InvalidTeacherDataException {
        System.out.print("Enter Teacher ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Teacher teacher = new Teacher(id, firstName, lastName, email);
        sisService.addTeacher(teacher);
        System.out.println("Teacher added successfully");
    }

    private static void enrollStudent() throws StudentNotFoundException, CourseNotFoundException, DuplicateEnrollmentException, InsufficientFundsException {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter Course ID: ");
        int courseID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Enrollment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        sisService.enrollStudentInCourse(studentID, courseID, date);
        System.out.println("Student enrolled successfully");
    }

    private static void assignTeacher() throws TeacherNotFoundException, CourseNotFoundException {
        System.out.print("Enter Teacher ID: ");
        int teacherID = scanner.nextInt();
        System.out.print("Enter Course ID: ");
        int courseID = scanner.nextInt();

        sisService.assignTeacherToCourse(teacherID, courseID);
        System.out.println("Teacher assigned successfully");
    }

    private static void recordPayment() throws StudentNotFoundException, PaymentValidationException {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        sisService.recordPayment(studentID, amount, date);
        System.out.println("Payment recorded successfully");
    }

    private static void generateEnrollmentReport() throws CourseNotFoundException, StudentNotFoundException {
        System.out.print("Enter Course ID: ");
        int courseID = scanner.nextInt();
        System.out.println(sisService.generateEnrollmentReport(courseID));
    }

    private static void generatePaymentReport() throws StudentNotFoundException {
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        System.out.println(sisService.generatePaymentReport(studentID));
    }

    private static void executeDynamicQuery() {
        System.out.print("Enter SQL Query: ");
        String query = scanner.nextLine();
        System.out.print("Enter parameters (comma-separated, or leave blank): ");
        String[] params = scanner.nextLine().isEmpty() ? new String[0] : scanner.nextLine().split(",");

        List<Object> results = sisService.executeDynamicQuery(query, (Object[]) params);
        for (Object result : results) {
            System.out.println(result);
        }
    }
}