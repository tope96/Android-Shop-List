package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    private EditText productName, price, count;
    private CheckBox bought;
    private FirebaseFirestore db;


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
        db = FirebaseFirestore.getInstance();
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }

    private void saveToDb(String productName, int productCount, int productPrice, boolean bought){

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



        ListItem product = new ListItem(name, pprice, pcount, bought.isChecked(), FirebaseAuth.getInstance().getCurrentUser().getUid());

        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TOMEK", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TOMEK", "Error adding document", e);
                    }
                });

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

