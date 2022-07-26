package com.client.conn.reservation;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class ReservationConv {
    private final ReservationAcc reservationAcc = Configuration.getRetrofit()
            .create(ReservationAcc.class);

    public List<Reservation> getAllReservations() throws IOException {
        return reservationAcc.findAllReservations()
                .execute()
                .body();
    }

    public Reservation getReservationById(Long id) throws IOException {
        return reservationAcc.findReservationById(id)
                .execute()
                .body();
    }

    public void createNewReservation(Reservation newReservation) throws IOException{
        reservationAcc.addNewReservation(newReservation)
                .execute()
                .body();
    }

    public void removeReservationByid(Long id) throws IOException{
        reservationAcc.deleteReservationById(id)
                .execute()
                .body();
    }
}
