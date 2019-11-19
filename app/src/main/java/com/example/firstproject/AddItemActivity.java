package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ComponentName;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);
        super.onCreate(savedInstanceState);
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

        String productNameText = productName.getText().toString();
        String countText = count.getText().toString();
        String priceText = price.getText().toString();


        if(productNameText.matches("") || countText.matches("") || priceText.matches("")){
            Toast.makeText(this, R.string.productEmpty, Toast.LENGTH_LONG).show();
            return;
        }




        String name = productNameText;
        int pcount = Integer.parseInt(countText);
        int pprice = Integer.parseInt(priceText);



        ListItem product = new ListItem(name, pprice, pcount, bought.isChecked());

        Intent intent = new Intent();
        intent.setAction("com.example.firstproject");
        intent.putExtra("product", name);
        intent.putExtra("price", pprice);
        intent.putExtra("count", pcount);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(
                new ComponentName("com.example.listener","com.example.listener.MainActivity$MyBroadcastReceiver"));
        sendBroadcast(intent);

        try {
            setResult(RESULT_OK);
            finish();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, R.string.productExists, Toast.LENGTH_SHORT).show();
        }
    }
}

