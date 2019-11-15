package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;
import com.example.firstproject.ProductDAO;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {
    public static final int RC_CREATE_PRODUCT = 1;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private SharedPreferences preferences;
    private ProductDAO productDAO;
    public static  MyAdapter adapter;
    public static int cos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        productDAO = Room.databaseBuilder(this, AppDatabase.class, "product")
                .allowMainThreadQueries()
                .build()
                .getProductDAO();

        rv = findViewById(R.id.rvItems);
        fab = findViewById(R.id.fabAddItem);

        LinearLayoutManager rlm = new LinearLayoutManager(this);
        rv.setLayoutManager(rlm);

        DividerItemDecoration divider = new DividerItemDecoration(rv.getContext(), rlm.getOrientation());
        rv.addItemDecoration(divider);

        adapter = new MyAdapter(getItems(), this);

        rv.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.updateData(getItems());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CREATE_PRODUCT && resultCode == RESULT_OK) {
            adapter.updateData(getItems());
        }
    }

    private List<ListItem> getItems(){
        List<ListItem> il = productDAO.getAll();
        return il;
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
