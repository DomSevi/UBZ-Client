package com.client.conn.room;

import com.client.conn.reservation.Reservation;

import java.util.List;

public class Room {
    private Long roomNr;
    private Long level;
    private Long capacity;
    private String type;

    private List<Reservation> reservations;

    public Room() {
    }

    public Room(Long level, Long capacity, String type) {
        this.level = level;
        this.capacity = capacity;
        this.type = type;
    }

    public Room(Long level, Long capacity, String type, List<Reservation> reservations) {
        this.level = level;
        this.capacity = capacity;
        this.type = type;
        this.reservations = reservations;
    }

    public Long getRoomNr() {
        return roomNr;
    }

    public void setRoomNr(Long roomNr) {
        this.roomNr = roomNr;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNr=" + roomNr +
                ", level=" + level +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
