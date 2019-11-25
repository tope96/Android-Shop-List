package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    private EditText productName, price, count;
    private CheckBox bought;
    private FirebaseFirestore db;
    Timestamp timestamp = new Timestamp(Timestamp.now().toDate());

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

    private void addProductToDb(ListItem product){

        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void saveNewItem(View view) {
        String productNameText = productName.getText().toString();
        String countText = count.getText().toString();
        String priceText = price.getText().toString();


        if (!validateForm(productNameText, countText, priceText)) {
            return;
        }

        String name = productNameText;
        int pcount = Integer.parseInt(countText);
        int pprice = Integer.parseInt(priceText);

        ListItem product = new ListItem(name, pprice, pcount, bought.isChecked(), FirebaseAuth.getInstance().getCurrentUser().getUid(), timestamp);

        addProductToDb(product);

        Intent intent = new Intent();
        intent.setAction("com.example.firstproject");
        intent.putExtra("product", name);
        intent.putExtra("price", pprice);
        intent.putExtra("count", pcount);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(
                new ComponentName("com.example.listener","com.example.listener.MainActivity$MyBroadcastReceiver"));
        sendBroadcast(intent);

        finish();
    }

    private boolean validateForm(String name, String count, String price) {
        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            productName.setError(getString(R.string.textRequire));
            valid = false;
        } else {
            productName.setError(null);
        }

        if (TextUtils.isEmpty(count)) {
            this.count.setError(getString(R.string.textRequire));
            valid = false;
        } else {
            this.count.setError(null);
        }

        if (TextUtils.isEmpty(price)) {
            this.price.setError(getString(R.string.textRequire));
            valid = false;
        } else {
            this.price.setError(null);
        }

    return valid;
    };
}

