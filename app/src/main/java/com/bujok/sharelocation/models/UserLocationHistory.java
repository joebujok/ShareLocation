package com.bujok.sharelocation.models;

/**
 * Created by Joe.Bujok on 13/10/2016.
 */

public class UserLocationHistory {

    public int userID;
    public long lat;
    public long lng;
    public long time;


    public UserLocationHistory() {
    }

    public UserLocationHistory(int userID, long lat, long lng, long time) {
        this.userID = userID;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }



}
