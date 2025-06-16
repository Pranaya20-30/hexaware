package com.example.cozy_heven.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Owners")
public class Owners {

    @Id
    @Column(name = "owner_id")
    private int ownerId; // Shared with Users.userId

    @OneToOne
    @MapsId
    @JoinColumn(name = "owner_id")
    private Users user;

   
    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "hotel_address", nullable = false)
    private String hotelAddress;

   
    @Column(name = "gst_number", length = 20)
    private String gstNumber;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private Timestamp registrationDate;

    // Constructors
    public Owners() {
    }

    public Owners(Users user, String hotelName, String hotelAddress, String gstNumber) {
        this.user = user;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.gstNumber = gstNumber;
    }

    // Lifecycle callback for automatic timestamping
    @PrePersist
    protected void onCreate() {
        this.registrationDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Owners{" +
                "ownerId=" + ownerId +
                ", hotelName='" + hotelName + '\'' +
                ", hotelAddress='" + hotelAddress + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
