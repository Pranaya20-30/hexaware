package com.hexaware.java.models;

public class Hotel {
    private int hotelId;
    private int ownerId;
    private String name;
    private String location;
    private String amenities;
    private java.sql.Timestamp createdAt;

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", amenities='" + amenities + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
