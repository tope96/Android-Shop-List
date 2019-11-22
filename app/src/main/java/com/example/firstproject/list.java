package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class list extends AppCompatActivity {
    public static final int RC_CREATE_PRODUCT = 1;
    private FloatingActionButton fab;
    private SharedPreferences preferences;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference productColl = db.collection("products");
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();
        Boolean str = in.getBooleanExtra("darkMode", false);
        changeTheme(str);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        fab = findViewById(R.id.fabAddItem);

        setUpRecyclerView();


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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

    private void setUpRecyclerView(){
        Query query = productColl.orderBy("name", Query.Direction.DESCENDING).whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirestoreRecyclerOptions<ListItem> options = new FirestoreRecyclerOptions.Builder<ListItem>()
                .setQuery(query, ListItem.class)
                .build();

        adapter = new RecyclerAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            private Drawable icon;
            private final ColorDrawable background =  new ColorDrawable(Color.RED);

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }



            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String documentId = adapter.getDocId(viewHolder.getAdapterPosition());
                db.collection("products")
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TOMEK", "onSuccess: Removed list item");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TOMEK", "onFailure: " + e.getLocalizedMessage());
                            }
                        });
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                icon = getDrawable(R.drawable.ic_delete_forever_black_24dp);

                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX > 0) { // Swiping to the right
                    int iconLeft = itemView.getLeft() + iconMargin;
                    int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
                } else if (dX < 0) { // Swiping to the left

                } else {
                    background.setBounds(0, 0, 0, 0);
                    icon.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
                icon.draw(c);
            }

        }).attachToRecyclerView(recyclerView);


    }
}
