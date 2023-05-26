package com.example.busreservationapp;

public class Trip {

    String busId, busName, asal, tujuan, harga, waktu, departTerminal, arrivalTerminal, timeDeparture, timeArrival;

    public Trip() {

    }

    public Trip(String busName, String asal, String tujuan, String harga, String departTerminal, String arrivalTerminal, String timeDeparture, String timeArrival, String waktu) {
        this.busName = busName;
        this.asal = asal;
        this.tujuan = tujuan;
        this.harga = harga;
        this.departTerminal = departTerminal;
        this.arrivalTerminal = arrivalTerminal;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.waktu = waktu;

    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getDepartTerminal() {
        return departTerminal;
    }

    public void setDepartTerminal(String departTerminal) {
        this.departTerminal = departTerminal;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }
}
