package com.hexaware.test;

import com.hexaware.dao.*;
import com.hexaware.service.*;
import com.hexaware.entity.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import com.hexaware.exception.*;


public class ReservationDaoTest {

    @Test
    public void testAssetIsReservedSuccessfully() {
        AssetManagementService dao = new AssetManagementServiceImpl();

        Reservation reservation = new Reservation(
            4   ,               // reservationId
            9,                  // assetId
            1,                  // employeeId
            LocalDate.now(),    // reservationDate
            LocalDate.now().plusDays(1),   // startDate
            LocalDate.now().plusDays(7),   // endDate
            "Reserved"          // status
        );

        try {
            boolean result = dao.insertReservation(reservation);
            Assertions.assertTrue(result, "Asset should be reserved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }
    
   
    

}

