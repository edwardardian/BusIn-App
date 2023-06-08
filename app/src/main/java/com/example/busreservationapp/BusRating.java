package com.example.busreservationapp;

public class BusRating {
    private String busName;
    private String ratingText;
    private float ratingStars;
    private String userName;

    private boolean hasRated;

    public BusRating() {
    }

    public BusRating(String busName, String ratingText, float ratingStars, String userName) {
        this.busName = busName;
        this.ratingText = ratingText;
        this.ratingStars = ratingStars;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
