package com.example.firstproject;

import android.content.ContentValues;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class ListItem {
    private int price;
    private int count;
    private boolean bought;
    @PrimaryKey
    @NonNull
    private String name;
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "name";

    public ListItem(String name, int price, int count, boolean bought) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.bought = bought;
    }

    public static ListItem fromContentValues(ContentValues values) {
        final ListItem listItem = new ListItem(values.getAsString("name"), values.getAsInteger("price"), values.getAsInteger("count"), values.getAsBoolean("bought"));
        if (values.containsKey(COLUMN_ID)) {
            listItem.name = values.getAsString(COLUMN_ID);
        }

        return listItem;
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
