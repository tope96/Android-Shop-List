package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecyclerAdapter extends FirestoreRecyclerAdapter<ListItem, RecyclerAdapter.ListHolder> {

    private Context context;

    public RecyclerAdapter(@NonNull FirestoreRecyclerOptions<ListItem> options, Context ctx) {
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
    protected void onBindViewHolder(@NonNull ListHolder holder, int position, @NonNull ListItem model) {
        holder.name.setText(model.getName());
        holder.count.setText("ilość: " + model.getCount()+"");
        holder.price.setText("cena: " + model.getPrice()+" zł");
        holder.bought.setChecked(model.isBought());
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new ListHolder(v);
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView price;
        public TextView count;
        public CheckBox bought;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            count = itemView.findViewById(R.id.tvCount);
            bought = itemView.findViewById(R.id.cbBought);
        }

        @Override
        public void onClick(View v) {
            String documentId = getSnapshots().getSnapshot(getAdapterPosition()).getId();
            Intent intent = new Intent(context, EditItemActivity.class);
            intent.putExtra("docId", documentId);
            context.startActivity(intent);
        }
    }
}
