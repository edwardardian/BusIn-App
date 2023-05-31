package com.example.busreservationapp;

public class Seats {

    public  Seats(){

    }
    private boolean isBooked;
    private String seatNo;
    private String id;

    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean booked) {
        isBooked = booked;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
