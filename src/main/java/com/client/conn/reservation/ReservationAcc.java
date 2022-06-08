package com.client.conn.reservation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ReservationAcc {
    
    @GET("reservations/list")
    Call<List<Reservation>> findAllReservations();

    @GET("reservations/{id}")
    Call<Reservation> findReservationById(@Path("id") Long id);
}
