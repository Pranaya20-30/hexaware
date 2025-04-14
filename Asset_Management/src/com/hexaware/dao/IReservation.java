
package com.hexaware.dao;

import com.hexaware.entity.Reservation;
import java.util.List;

public interface IReservation {
    boolean insertReservation(Reservation reservation) throws Exception;
    boolean deleteReservation(int reservationId) throws Exception;
    Reservation getReservationById(int reservationId) throws Exception;
    List<Reservation> getAllReservations() throws Exception;
}
