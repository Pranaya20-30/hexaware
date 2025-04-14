package com.hexaware.dao;

import com.hexaware.entity.Asset;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import com.hexaware.util.DBConnUtil;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
                // Asset ID not found, throw custom exception
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

    @Override
    public boolean allocateAsset(int assetId, int employeeId, LocalDate allocationDate) {
        String query = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setInt(2, employeeId);
            ps.setDate(3, java.sql.Date.valueOf(allocationDate));

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

        // Check if cost is valid
        if (cost <= 0) {
            throw new AssetNotMaintainException("Maintenance cost must be greater than 0.");
        }

        // Check if asset exists
        String checkQuery = "SELECT COUNT(*) FROM assets WHERE asset_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, assetId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
            }
        }

        // Proceed to insert maintenance record
        String query = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, assetId);
            ps.setDate(2, java.sql.Date.valueOf(maintenanceDate));
            ps.setString(3, description);
            ps.setDouble(4, cost);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error recording maintenance: " + e.getMessage());
            throw e; 
        }
    }

    @Override
    public boolean reserveAsset(int assetId, int employeeId, LocalDate reservationDate, LocalDate startDate, LocalDate endDate) {
        String query = "INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
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
        String query = "UPDATE reservations SET status='canceled' WHERE reservation_id=?";
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
		return null;
	}
}
