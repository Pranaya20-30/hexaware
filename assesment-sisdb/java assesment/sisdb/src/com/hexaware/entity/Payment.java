package com.hexaware.entity;



import java.time.LocalDate;

public class Payment {
    private int paymentID;
    private Student student;
    private double amount;
    private LocalDate paymentDate;

    public Payment(int paymentID, Student student, double amount, LocalDate paymentDate) {
        this.paymentID = paymentID;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters
    public int getPaymentID() { return paymentID; }
    public Student getStudent() { return student; }
    public double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
}