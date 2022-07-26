package com.client.conn.reservation;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ReservationAcc {
    
    @GET("/reservations/list")
    Call<List<Reservation>> findAllReservations();

    @GET("/reservations/{id}")
    Call<Reservation> findReservationById(@Path("id") Long id);

    @POST("/reservations/add")
    Call<Void> addNewReservation(@Body Reservation newReservation);

    @DELETE("/reservations/delete/{id}")
    Call<Void> deleteReservationById(@Path("id") Long id);
}
