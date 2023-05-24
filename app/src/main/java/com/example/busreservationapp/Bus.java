package com.example.busreservationapp;

public class Bus {

    String busName, busPhoto, busRating;

    int seats;

    public Bus() {

    }

    public Bus(String busName, String busPhoto, String busRating, int seats) {
        this.busName = busName;
        this.busPhoto = busPhoto;
        this.busRating = busRating;
        this.seats = seats;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusPhoto() {
        return busPhoto;
    }

    public void setBusPhoto(String busPhoto) {
        this.busPhoto = busPhoto;
    }

    public String getBusRating() {
        return busRating;
    }

    public void setBusRating(String busRating) {
        this.busRating = busRating;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
