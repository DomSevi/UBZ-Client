package com.client.conn.room;

import com.client.conn.config.Configuration;

import java.io.IOException;
import java.util.List;

public class RoomConv {
    private final RoomAcc roomAcc = Configuration.getRetrofit()
            .create(RoomAcc.class);

    public List<Room> getAllRooms() throws IOException {
        return roomAcc.findAllRooms()
                .execute()
                .body();
    }

    public Room getRoomByNr(Long nr) throws IOException{
        return roomAcc.findRoomByNr(nr)
                .execute()
                .body();
    }

    public void createNewRoom(Room room) throws IOException{
        roomAcc.addNewRoom(room)
                .execute()
                .body();
    }

    public void removeRoomByNr(Long nr) throws IOException{
        roomAcc.deleteRoomByNr(nr)
                .execute()
                .body();
    }
}
