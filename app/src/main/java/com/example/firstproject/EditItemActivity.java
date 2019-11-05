package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.firstproject.list.adapter;

public class EditItemActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText name, price, count;
    private CheckBox bought;
    private ProductDAO productDAO;
    private String names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent in = getIntent();

        productDAO = Room.databaseBuilder(this, AppDatabase.class, "product")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getContactDAO();

        int itemPosition = in.getIntExtra("itemPosition", 1);



        name = findViewById(R.id.etEditName);
        price = findViewById(R.id.etEditPrice);
        count = findViewById(R.id.etEditCount);
        bought = findViewById(R.id.cbEditBought);

        name.setText(productDAO.select(getItem(itemPosition)).getName());
        price.setText(productDAO.select(getItem(itemPosition)).getPrice()+"");
        count.setText(productDAO.select(getItem(itemPosition)).getCount()+"");
        bought.setChecked(productDAO.select(getItem(itemPosition)).isBought());

        names = productDAO.select(getItem(itemPosition)).getName();

    }

    @Override
    protected void onStart() {
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);
        super.onStart();
    }

    private String getItem(int position){
        List<ListItem> il = productDAO.getAll();

        return il.get(position).getName();
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }

    private List<ListItem> getItems(){
        List<ListItem> il = productDAO.getAll();
        return il;
    }

    public void saveEditedItem(View view) {
        String pName = name.getText().toString();
        int pCount = Integer.parseInt(count.getText().toString());
        int pPrice = Integer.parseInt(price.getText().toString());
        boolean pBought = false;

        if(bought.isChecked()){
            pBought = true;
        }else{
            pBought = false;
        }

        try {
            productDAO.updateRow(pName, pPrice, pCount, pBought, names);
            setResult(RESULT_OK);
            finish();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Taki produkt już istnieje. Wybierz inną nazwę.", Toast.LENGTH_SHORT).show();
        }
    }
}
