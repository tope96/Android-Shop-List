package com.example.firstproject;

import com.google.firebase.firestore.GeoPoint;

public class Shop {

    private String name;
    private String desc;
    private int radius;
    private double longitude;
    private double latitude;
    private String uid;

    public Shop(String name, String desc, int radius, double longitude, double latitude, String uid) {
        this.name = name;
        this.desc = desc;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
        this.uid = uid;
    }

    public Shop(){

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

