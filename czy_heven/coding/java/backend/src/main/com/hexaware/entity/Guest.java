package com.hexaware.java.models;

public class Guest {
    private int guestId;
    private int bookingId;
    private String name;
    private int age;

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Guest{" +
                "guestId=" + guestId +
                ", bookingId=" + bookingId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
