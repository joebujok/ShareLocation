package com.bujok.sharelocation.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe.Bujok on 13/10/2016.
 */

public class UserLocationHistory {

    public String userUID;

    public float accuracy;
    public double altitude;
    public float bearing;
    public float speed;



    public double lat;
    public double lng;
    public long time;


    public UserLocationHistory() {
    }

    public UserLocationHistory(float accuracy, double altitude, float bearing, double lat, double lng, float speed, long time, String userID) {
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.bearing = bearing;
        this.lat = lat;
        this.lng = lng;
        this.speed = speed;
        this.time = time;
        this.userUID = userID;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userUID", userUID);
        result.put("accuracy", accuracy);
        result.put("altitude", altitude);
        result.put("bearing", bearing);
        result.put("speed", speed);
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("time", time);

        return result;
    }
    // [END post_to_map]

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
