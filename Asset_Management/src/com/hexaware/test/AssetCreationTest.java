package com.hexaware.test;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaware.dao.AssetManagementService;
import com.hexaware.dao.AssetManagementServiceImpl;
import com.hexaware.entity.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AssetCreationTest {

    private AssetManagementService service;

    @BeforeEach
    public void setUp() {
        service = new AssetManagementServiceImpl();
    }

    @Test
    public void testAssetCreation() throws SQLException {
        // Setup
        Asset asset = new Asset(91, "Laptop", "Electronics", "kyn", LocalDate.now(), "New York", "Available", 1);

        // Test
        boolean result = service.addAsset(asset);

        // Verify
        assertTrue(result, "Asset should be created successfully.");
    }
}