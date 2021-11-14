package com.example.zwiggy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Data.ResDetail;
import com.example.zwiggy.Data.Restaurant;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;
import com.example.zwiggy.UI.CustomerActivity;
import com.example.zwiggy.UI.CustomerMenuRActivity;

import java.util.ArrayList;

import io.realm.mongodb.User;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerAdapterViewHolder> {

    ArrayList<Restaurant> mResturants;
    Context mContext;

    public CustomerAdapter(Context context, ArrayList<Restaurant> resturantsList){
        mContext = context;
        mResturants = resturantsList;
    }
    @NonNull
    @Override
    public CustomerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.activity_customer_restaurant_list_iems, parent, false);
        CustomerAdapterViewHolder viewHolder = new CustomerAdapterViewHolder(contactView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomerAdapterViewHolder holder, int position) {
        Restaurant restaurant = mResturants.get(position);
        holder.restaurantName.setText(restaurant.getName());
        holder.restaurantLocation.setText(restaurant.getLocation());

    }

    @Override
    public int getItemCount() {
        return mResturants.size();
    }

    public class CustomerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView restaurantName;
        public TextView restaurantLocation;

        public CustomerAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantLocation = itemView.findViewById(R.id.restaurant_location);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            UserDetail.setmcusresId(mResturants.get(pos).getId());
            ResDetail.setResname((mResturants.get(pos).getName()));
            ResDetail.setResminAmnt(mResturants.get(pos).getMinAmnt());
            ResDetail.setResownerid(mResturants.get(pos).getId());
            Log.i("pos", mResturants.get(pos).getName());
            Intent intent = new Intent(mContext, CustomerMenuRActivity.class);
            mContext.startActivity(intent);
        }
    }
}