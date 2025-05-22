package com.example.a91map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> itemList;
    private DBHelper dbHelper;

    public ItemAdapter(ArrayList<Item> itemList, DBHelper dbHelper) {
        this.itemList = itemList;
        this.dbHelper = dbHelper;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription, textStatus, textContact;
        Button btnDelete;

        public ViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textDescription = view.findViewById(R.id.textDescription);
            textStatus = view.findViewById(R.id.textStatus);
            textContact = view.findViewById(R.id.textContact);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textTitle.setText(item.getTitle());
        holder.textDescription.setText(item.getDescription());
        holder.textStatus.setText(item.getStatus());
        holder.textContact.setText(item.getContact());

        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteItem(item.getId());
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
