package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    private EditText productName, price, count;
    private CheckBox bought;
    private ProductDAO productDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);
        super.onCreate(savedInstanceState);

        productDAO = Room.databaseBuilder(this, AppDatabase.class, "product")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getContactDAO();

        setContentView(R.layout.activity_add_item);

        productName = findViewById(R.id.etAddName);
        price = findViewById(R.id.etAddPrice);
        count = findViewById(R.id.etAddCount);
        bought = findViewById(R.id.cbAddBought);
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }

    public void saveNewItem(View view) {
        String name = productName.getText().toString();
        int pcount = Integer.parseInt(count.getText().toString());
        int pprice = Integer.parseInt(price.getText().toString());
        boolean pbought = false;

        if(bought.isChecked()){
            pbought = true;
        }else{
            pbought = false;
        }

        ListItem product = new ListItem(name, pprice, pcount, pbought);

        try {
            productDAO.insertAll(product);
            setResult(RESULT_OK);
            finish();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Taki produkt już istnieje. Wybierz inną nazwę.", Toast.LENGTH_SHORT).show();
        }
    }
}

