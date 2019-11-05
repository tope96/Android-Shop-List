package com.example.firstproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM product")
    List<ListItem> getAll();

    @Query("SELECT * FROM product WHERE Name LIKE (:productsNames)")
    ListItem select(String productsNames);

    @Query("UPDATE product SET Name = (:newName), Price = (:newPrice), Count = (:newCount), Bought = (:newBought) WHERE Name = (:oldName)")
    void updateRow(String newName, int newPrice, int newCount, boolean newBought, String oldName);


    @Insert
    void insertAll(ListItem... listItems);

    @Delete
    void delete(ListItem listItem);

    @Update
    void update(ListItem listItem);


}
