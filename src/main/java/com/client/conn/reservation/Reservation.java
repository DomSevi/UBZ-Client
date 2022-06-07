package com.client.conn.reservation;

import java.util.Date;

public class Reservation {
    private Long reservationId;
    private Date from;
    private Date to;
    private Long roomId;
    private Long empId;

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", from=" + from +
                ", to=" + to +
                ", roomId=" + roomId +
                ", empId=" + empId +
                '}';
    }

    public Reservation() {
    }

    public Reservation(Long reservationId, Date from, Date to, Long roomId, Long empId) {
        this.reservationId = reservationId;
        this.from = from;
        this.to = to;
        this.roomId = roomId;
        this.empId = empId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
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
}
