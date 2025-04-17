package com.hexaware.service;

import com.hexaware.entity.Asset;
import com.hexaware.entity.Reservation;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.util.DBConnUtil;
import com.hexaware.dao.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AssetManagementServiceImpl implements AssetManagementService {

    @Override
    public boolean addAsset(Asset asset) {
        String query = "INSERT INTO assets (name, type, serial_number, purchase_date, location, status, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, asset.getName());
            ps.setString(2, asset.getType());
            ps.setString(3, asset.getSerialNumber());
            ps.setDate(4, Date.valueOf(asset.getPurchaseDate()));
            ps.setString(5, asset.getLocation());
            ps.setString(6, asset.getStatus());
            ps.setInt(7, asset.getOwnerId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAsset(Asset asset) {
        String query = "UPDATE assets SET name=?, type=?, serial_number=?, purchase_date=?, location=?, status=?, owner_id=? WHERE asset_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, asset.getName());
            ps.setString(2, asset.getType());
            ps.setString(3, asset.getSerialNumber());
            ps.setDate(4, Date.valueOf(asset.getPurchaseDate()));
            ps.setString(5, asset.getLocation());
            ps.setString(6, asset.getStatus());
            ps.setInt(7, asset.getOwnerId());
            ps.setInt(8, asset.getAssetId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAsset(int assetId) {
        String query = "DELETE FROM assets WHERE asset_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new AssetNotFoundException("Asset ID " + assetId + " not found.");
            }

            System.out.println("Asset deleted successfully.");
            return true;

        } catch (AssetNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return false;

        } catch (SQLException e) {
            System.out.println("SQL Error deleting asset: " + e.getMessage());
            return false;
        }
    }

  
    public boolean allocateAsset(int assetId, int employeeId, LocalDate allocationDate, LocalDate returnDate ) {
        String query = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date , return_date) VALUES (?, ?, ?,?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setInt(2, employeeId);
            ps.setDate(3, java.sql.Date.valueOf(allocationDate));
            ps.setDate(4,java.sql.Date.valueOf(returnDate));
            

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error allocating asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deallocateAsset(int assetId, int employeeId, LocalDate returnDate) {
        String query = "UPDATE asset_allocations SET return_date=? WHERE asset_id=? AND employee_id=? AND return_date IS NULL";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(returnDate));
            ps.setInt(2, assetId);
            ps.setInt(3, employeeId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deallocating asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean performMaintenance(int assetId, LocalDate maintenanceDate, String description, double cost)
            throws AssetNotFoundException, AssetNotMaintainException, SQLException {

        if (cost <= 0) {
            throw new AssetNotMaintainException("Maintenance cost must be greater than 0.");
        }

        String checkQuery = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, assetId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
            }
        }

        String query = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setDate(2, java.sql.Date.valueOf(maintenanceDate));
            ps.setString(3, description);
            ps.setDouble(4, cost);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error recording maintenance: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean reserveAsset(int assetId, int employeeId, LocalDate reservationDate, LocalDate startDate, LocalDate endDate) {
        String query = "INSERT INTO reservation (asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setInt(2, employeeId);
            ps.setDate(3, java.sql.Date.valueOf(reservationDate));
            ps.setDate(4, java.sql.Date.valueOf(startDate));
            ps.setDate(5, java.sql.Date.valueOf(endDate));
            ps.setString(6, "pending");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error reserving asset: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean withdrawReservation(int reservationId) {
        String query = "UPDATE reservation SET status='canceled' WHERE reservation_id=?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reservationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error withdrawing reservation: " + e.getMessage());
            return false;
        }
    }

    public List<Asset> getAllAssets() {
        String query = "SELECT * FROM assets";
        List<Asset> assets = new ArrayList<>();

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asset asset = new Asset(
                    rs.getInt("asset_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("serial_number"),
                    rs.getDate("purchase_date").toLocalDate(),
                    rs.getString("location"),
                    rs.getString("status"),
                    rs.getInt("owner_id")
                );
                assets.add(asset);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching assets: " + e.getMessage());
        }

        return assets;
    }
    
    
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

    public List<Reservation> getAllReservations() throws Exception {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Safely convert SQL Date to LocalDate
                java.sql.Date resDateSql = rs.getDate("reservation_date");
                java.sql.Date startDateSql = rs.getDate("start_date");
                java.sql.Date endDateSql = rs.getDate("end_date");

                LocalDate resDate = resDateSql != null ? resDateSql.toLocalDate() : null;
                LocalDate startDate = startDateSql != null ? startDateSql.toLocalDate() : null;
                LocalDate endDate = endDateSql != null ? endDateSql.toLocalDate() : null;

                Reservation r = new Reservation(
                    rs.getInt("reservation_id"),
                    rs.getInt("asset_id"),
                    rs.getInt("employee_id"),
                    resDate,
                    startDate,
                    endDate,
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
 


    @Override
    public boolean addEmployee(String name, String email, String department, String password) {
        String sql = "INSERT INTO employees (name, department, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set values for the prepared statement
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, email);
            
            ps.setString(4, password);

            // Execute the insert query
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }

	

	
	}

