package com.example.cozy_heven.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import com.example.cozy_heven.util.PrivilegeConverter;

@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @Column(name = "admin_id")
    private int adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private Users user;

    private String designation;

    @Column(columnDefinition = "json")
    @Convert(converter = PrivilegeConverter.class)
    private List<String> privileges;

    @Column(name = "joined_on")
    private Timestamp joinedOn = new Timestamp(System.currentTimeMillis());

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public Timestamp getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Timestamp joinedOn) {
        this.joinedOn = joinedOn;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", designation='" + designation + '\'' +
                ", privileges=" + privileges +
                ", joinedOn=" + joinedOn +
                '}';
    }
}
