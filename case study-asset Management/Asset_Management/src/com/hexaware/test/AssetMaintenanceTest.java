package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaware.dao.AssetManagementService;
import com.hexaware.service.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import com.hexaware.exception.AssetNotFoundException;
import com.hexaware.exception.AssetNotMaintainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetMaintenanceTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetMaintenance() throws AssetNotMaintainException, SQLException, AssetNotFoundException {
        // Setup
    	

    	Asset asset = new Asset(
    	    4,
    	    "Updated Laptop",
    	    "Laptop",
    	    "8i998",
    	    LocalDate.parse("2025-01-01"), // ✅ Fixed here
    	    "Mumbai",
    	    "under maintenance",
    	    1
    	);
        service.addAsset(asset);  // Ensure the asset exists

        // Test
        boolean result = service.performMaintenance(asset.getAssetId(), LocalDate.now(), "Repair", 150.0);

        // Verify
        assertTrue(result, "Asset should be added to maintenance successfully.");
    }
}