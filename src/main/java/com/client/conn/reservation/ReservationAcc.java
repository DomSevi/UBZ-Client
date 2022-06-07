package com.client.conn.reservation;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ReservationAcc {
    
    @GET("/reservations/list")
    Call<List<Reservation>> findAllReservations();

    Call<Reservation> findReservationById(Long id);
}
