package com.example.firstproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ListItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO getContactDAO();
}