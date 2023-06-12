package com.example.busreservationapp;

public class BusRating {
    private String busName, ratingText, userName, userId, passengers, date;
    private float ratingStars;
    private boolean hasRated;

    public BusRating() {
    }

    public BusRating(String busName, String ratingText, float ratingStars, String userName, String userId, String passengers, String date) {
        this.busName = busName;
        this.ratingText = ratingText;
        this.ratingStars = ratingStars;
        this.userName = userName;
        this.userId = userId;
        this.passengers = passengers;
        this.date = date;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public float getRatingStars() {
        return ratingStars;
    }

    public void setRatingStars(float ratingStars) {
        this.ratingStars = ratingStars;
    }

    public boolean hasRated() {
        return hasRated;
    }

    public void setHasRated(boolean hasRated) {
        this.hasRated = hasRated;
    }
}
