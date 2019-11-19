package com.example.firstproject;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class ListItem {
    private int price;
    private int count;
    private boolean bought;
    private String name;
    public static final String COLUMN_ID = BaseColumns._ID;



    public ListItem(String name, int price, int count, boolean bought) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.bought = bought;
    }



    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

}
