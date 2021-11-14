package com.example.zwiggy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Data.MenuItem;
import com.example.zwiggy.Data.NewOrderMenuItem;
import com.example.zwiggy.Data.Restaurant;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;
import com.example.zwiggy.UI.CreateNewOrder;
import com.example.zwiggy.UI.CustomerMenuRActivity;

import java.util.ArrayList;

public class CreateNewOrderAdapter extends RecyclerView.Adapter<CreateNewOrderAdapter.CreateNewOrderAdapterViewHolder> {

    ArrayList<NewOrderMenuItem> mNewOrder;
    Context mContext;

    public CreateNewOrderAdapter(Context context, ArrayList<NewOrderMenuItem> orders){
        mContext = context;
        mNewOrder = orders;
    }
    @NonNull
    @Override
    public CreateNewOrderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_create_new_order_list_items, parent, false);
        CreateNewOrderAdapterViewHolder viewHolder = new CreateNewOrderAdapterViewHolder(contactView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CreateNewOrderAdapterViewHolder holder, int position) {
        NewOrderMenuItem item = mNewOrder.get(position);
        holder.menuItemName.setText(item.getItemName());
        holder.menuItemPrice.setText("Rs. "+Integer.toString(item.getPrice()));
        holder.menuItemQuantity.setText("Quantity:- "+Integer.toString(item.getQuatity()));
        holder.menuItemTotal.setText(Integer.toString(item.getPrice()*item.getQuatity()));
    }

    @Override
    public int getItemCount() {
        return mNewOrder.size();
    }

    public class CreateNewOrderAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView menuItemName;
        public TextView menuItemQuantity;
        public TextView menuItemPrice;
        public TextView menuItemTotal;

        public CreateNewOrderAdapterViewHolder(View itemView) {
            super(itemView);
            menuItemName=itemView.findViewById(R.id.new_order_item_name);
            menuItemQuantity=itemView.findViewById(R.id.new_order_item_quantity);
            menuItemPrice=itemView.findViewById(R.id.new_order_item_price);
            menuItemTotal=itemView.findViewById(R.id.item_total);
        }

    }
}