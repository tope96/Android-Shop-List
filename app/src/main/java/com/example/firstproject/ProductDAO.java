package com.example.firstproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM product")
    List<ListItem> getAll();

    @Query("SELECT * FROM product WHERE Name IN (:productsNames)")
    List<ListItem> loadAllByIds(String[] productsNames);

    @Insert
    void insertAll(ListItem... listItems);

    @Delete
    void delete(ListItem listItem);

}
