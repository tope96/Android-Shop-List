package com.example.firstproject;

public class Shop {

    private String name;
    private String desc;
    private int radius;

    public Shop(String name, String desc, int radius) {
        this.name = name;
        this.desc = desc;
        this.radius = radius;
    }

    public Shop(){

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




}

