package com.hexaware.java.models;

public class Room {
    private int roomId;
    private int hotelId;
    private String roomSize;
    private String bedType;
    private int capacity;
    private double baseFare;
    private boolean isAc;
    private java.sql.Timestamp createdAt;

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getRoomSize() { return roomSize; }
    public void setRoomSize(String roomSize) { this.roomSize = roomSize; }

    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getBaseFare() { return baseFare; }
    public void setBaseFare(double baseFare) { this.baseFare = baseFare; }

    public boolean isAc() { return isAc; }
    public void setAc(boolean ac) { isAc = ac; }

    public java.sql.Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.sql.Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", hotelId=" + hotelId +
                ", roomSize='" + roomSize + '\'' +
                ", bedType='" + bedType + '\'' +
                ", capacity=" + capacity +
                ", baseFare=" + baseFare +
                ", isAc=" + isAc +
                ", createdAt=" + createdAt +
                '}';
    }
}
