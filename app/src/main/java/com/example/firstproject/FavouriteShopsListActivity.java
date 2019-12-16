package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FavouriteShopsListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference shopsColl = db.collection("shops");
    private RecyclerAdapterShops adapter;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_shops_list);
        toolbar = getSupportActionBar();

        loadFragment(new Frag_list());
        BottomNavigationView navigation = findViewById(R.id.navig);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()){
            case R.id.shopList:
                fragment = new Frag_list();
                break;

            case R.id.shopMap:
                fragment = new Frag_map();
                break;
        }

        return loadFragment(fragment);
    }
}
