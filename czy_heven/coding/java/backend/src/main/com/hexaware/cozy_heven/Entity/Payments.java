package com.example.cozy_heven.entity;



public class Payments {
    private int paymentId;
    private int bookingId;
    private double amount;
    private java.sql.Timestamp paymentDate;
    private String method;
    private String status;

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public java.sql.Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(java.sql.Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", bookingId=" + bookingId +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
}
