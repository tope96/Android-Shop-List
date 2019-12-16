package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecyclerAdapterShops extends FirestoreRecyclerAdapter<Shop, RecyclerAdapterShops.ListHolder> {

    private Context context;

    public RecyclerAdapterShops(@NonNull FirestoreRecyclerOptions<Shop> options, Context ctx) {
        super(options);
        this.context = ctx;
    }


    public String getDocId(int position) {
        String documentId = getSnapshots().getSnapshot(position).getId();
        return documentId;
    }

    public Context getContext(){
        return context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder holder, int position, @NonNull Shop model) {
        holder.name.setText(model.getName()+"");
        holder.desc.setText(model.getDesc()+"");
        holder.radius.setText(model.getRadius()+" m");
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shop, parent, false);
        return new ListHolder(v);
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView desc;
        public TextView radius;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvShop);
            desc = itemView.findViewById(R.id.tvDescShop);
            radius = itemView.findViewById(R.id.tvRadiusShop);
        }

        @Override
        public void onClick(View v) {

        }
    }
}