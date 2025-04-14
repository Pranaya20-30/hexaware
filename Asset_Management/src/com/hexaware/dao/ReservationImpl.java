package com.hexaware.dao;

import com.hexaware.entity.Reservation;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationImpl implements IReservation {

    @Override
    public boolean insertReservation(Reservation reservation) throws Exception {
        String sql = "INSERT INTO reservation (reservation_id, asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservation.getReservationId());
            ps.setInt(2, reservation.getAssetId());
            ps.setInt(3, reservation.getEmployeeId());
            ps.setDate(4, Date.valueOf(reservation.getReservationDate()));
            ps.setDate(5, Date.valueOf(reservation.getStartDate()));
            ps.setDate(6, Date.valueOf(reservation.getEndDate()));
            ps.setString(7, reservation.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteReservation(int reservationId) throws Exception {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Reservation getReservationById(int reservationId) throws Exception {
        String sql = "SELECT * FROM reservation WHERE reservation_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("asset_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() throws Exception {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservation r = new Reservation(
                    rs.getInt("reservation_id"),
                    rs.getInt("asset_id"),
                    rs.getInt("employee_id"),
                    rs.getDate("reservation_date").toLocalDate(),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("status")
                );
                list.add(r);
            }
        }
        return list;
    }

    public boolean reserveAsset(Reservation reservation) throws Exception, EmployeeNotFoundException, AssetNotFoundException {
        try (Connection conn = DBConnUtil.getConnection()) {
            // Check if employee exists
            try (PreparedStatement psEmp = conn.prepareStatement("SELECT * FROM employees WHERE employee_id = ?")) {
                psEmp.setInt(1, reservation.getEmployeeId());
                try (ResultSet rs = psEmp.executeQuery()) {
                    if (!rs.next()) {
                        throw new EmployeeNotFoundException("Employee with ID " + reservation.getEmployeeId() + " not found.");
                    }
                }
            }

            // Check if asset exists
            try (PreparedStatement psAsset = conn.prepareStatement("SELECT * FROM assets WHERE asset_id = ?")) {
                psAsset.setInt(1, reservation.getAssetId());
                try (ResultSet rs = psAsset.executeQuery()) {
                    if (!rs.next()) {
                        throw new AssetNotFoundException("Asset with ID " + reservation.getAssetId() + " not found.");
                    }
                }
            }

            // Insert reservation if employee and asset are found
            return insertReservation(reservation);
        }
    }
}
