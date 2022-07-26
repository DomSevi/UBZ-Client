package com.client.conn.reservation;

import java.util.Date;

public class Reservation {
    private Long reservationId;
    private Long day;
    private Long hour;
    private Long roomId;
    private Long empId;

    public Reservation() {
    }

    public Reservation(Long day, Long hour, Long roomId, Long empId) {
        this.day = day;
        this.hour = hour;
        this.roomId = roomId;
        this.empId = empId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", day=" + day +
                ", hour=" + hour +
                ", roomId=" + roomId +
                ", empId=" + empId +
                '}';
    }
}
