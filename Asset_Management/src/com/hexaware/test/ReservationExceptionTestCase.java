package com.hexaware.test;

import com.hexaware.dao.ReservationImpl;
import com.hexaware.entity.Reservation;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.exception.AssetNotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationExceptionTestCase {

    private final ReservationImpl reservationService = new ReservationImpl();

    @Test
    public void testEmployeeNotFoundException() {
        int validAssetId = 1;             // assume this exists in DB
        int nonExistentEmployeeId = 9999; // assume this does not exist

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate reservationDate = LocalDate.now();

        // Create a Reservation object
        Reservation reservation = new Reservation(0, validAssetId, nonExistentEmployeeId, reservationDate, startDate, endDate, "Reserved");

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            reservationService.reserveAsset(reservation);  // Pass the reservation object
        });

        String expectedMessage = "Employee with ID " + nonExistentEmployeeId + " not found";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testAssetNotFoundException() {
        int nonExistentAssetId = 9999;  // assume this does not exist
        int validEmployeeId = 1;        // assume this exists in DB

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate reservationDate = LocalDate.now();

        // Create a Reservation object
        Reservation reservation = new Reservation(0, nonExistentAssetId, validEmployeeId, reservationDate, startDate, endDate, "Reserved");

        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
            reservationService.reserveAsset(reservation);  // Pass the reservation object
        });

        String expectedMessage = "Asset with ID " + nonExistentAssetId + " not found";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
