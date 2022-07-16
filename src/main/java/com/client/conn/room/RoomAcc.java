package com.client.conn.room;

import com.client.conn.employee.Employee;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RoomAcc {
    @GET("/rooms/list")
    Call<List<Room>> findAllRooms();

    @GET("/rooms/{nr}")
    Call<Room> findRoomByNr(@Path("nr") Long nr);

    @POST("/rooms/add")
    Call<Room> addNewRoom(@Body Room newRoom);

    @DELETE("/rooms/delete/{nr}")
    Call<Room> deleteRoomByNr(@Path("nr") Long nr);
}
