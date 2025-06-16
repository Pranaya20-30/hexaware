package com.example.cozy_heven.entity;

public class Bookings {
    private int bookingId;
    private int userId;
    private int roomId;
    private java.sql.Date checkInDate;
    private java.sql.Date checkOutDate;
    private int totalGuests;
    private double totalFare;
    private String status;
    private java.sql.Timestamp createdAt;

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public java.sql.Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(java.sql.Date checkInDate) { this.checkInDate = checkInDate; }

    public java.sql.Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(java.sql.Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getTotalGuests() { return totalGuests; }
    public void setTotalGuests(int totalGuests) { this.totalGuests = totalGuests; }

    public double getTotalFare() { return totalFare; }
    public void setTotalFare(double totalFare) { this.totalFare = totalFare; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalGuests=" + totalGuests +
                ", totalFare=" + totalFare +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}