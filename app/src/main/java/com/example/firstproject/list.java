package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class list extends AppCompatActivity {
    public static final int RC_CREATE_PRODUCT = 1;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private SharedPreferences preferences;
    //private ProductDAO productDAO;
    public static  MyAdapter adapter;
    private static FirebaseFirestore mDatabase;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        rv = findViewById(R.id.rvItems);
        fab = findViewById(R.id.fabAddItem);

        LinearLayoutManager rlm = new LinearLayoutManager(this);
        rv.setLayoutManager(rlm);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), rlm.getOrientation());
        rv.addItemDecoration(divider);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CREATE_PRODUCT && resultCode == RESULT_OK) {
            //adapter.updateData(getItems());
        }
    }


    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }


    public void addItem(View view) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        Intent addItem = new Intent(this, AddItemActivity.class);
        addItem.putExtra("darkMode", str);
        startActivityForResult(addItem, RC_CREATE_PRODUCT);
    }
}
