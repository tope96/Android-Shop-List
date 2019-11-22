package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditItemActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ProgressBar progressBar;
    private EditText name, price, count;
    private CheckBox bought;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        name = findViewById(R.id.etEditName);
        price = findViewById(R.id.etEditPrice);
        count = findViewById(R.id.etEditCount);
        bought = findViewById(R.id.cbEditBought);
        progressBar = findViewById(R.id.pbEdit);

        Intent in = getIntent();
        documentId = in.getStringExtra("docId");

        loadData(documentId);

    }

    @Override
    protected void onStart() {
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);
        super.onStart();
    }

    private void loadData(String docId){
        showProgress();
        DocumentReference docRef = db.collection("products").document(docId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ListItem product = documentSnapshot.toObject(ListItem.class);
                name.setText(product.getName());
                count.setText(product.getCount()+"");
                price.setText(product.getPrice()+"");
                bought.setChecked(product.isBought());
                hideProgress();
            }
        });
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }


    public void saveEditedItem(View view) {
        String productNameText = name.getText().toString();
        String countText = count.getText().toString();
        String priceText = price.getText().toString();

        if(productNameText.matches("") || countText.matches("") || priceText.matches("")){
            Toast.makeText(this, R.string.productEmpty, Toast.LENGTH_LONG).show();
            return;
        }

        String pName = productNameText;
        int pCount = Integer.parseInt(countText);
        int pPrice = Integer.parseInt(priceText);

        DocumentReference documentReference = db.collection("products").document(documentId);

        try {
            documentReference.update("name", pName,
                    "count", pCount,
                    "price", pPrice,
                    "bought", bought.isChecked());
            setResult(RESULT_OK);
            finish();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, R.string.productExists, Toast.LENGTH_SHORT).show();
        }
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }
}
