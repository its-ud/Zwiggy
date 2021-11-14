package com.example.zwiggy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Data.OrderHistory;
import com.example.zwiggy.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    ArrayList<OrderHistory> history;
    Context context;
    public HistoryAdapter(Context context, ArrayList<OrderHistory> history) {
        this.history = history;
        this.context = context;
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.history_item, parent, false);
        HistoryAdapter.HistoryViewHolder viewHolder = new HistoryAdapter.HistoryViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.historyRestaurantName.setText(history.get(position).getRestaurantName());
        holder.historyOrderId.setText(history.get(position).getOrderId());
        int status=history.get(position).getStatus();
        switch (status){
            case 0:holder.historyStatus.setText("Pending");
                    holder.historyStatus.setTextColor(Integer.parseInt("#FFA500"));
                    break;
            case 1:holder.historyStatus.setText("Rejected");
                    holder.historyStatus.setTextColor(Integer.parseInt("#FF0000"));
                    break;
            case 2: holder.historyStatus.setText("");
                    holder.historyStatus.setTextColor(Integer.parseInt("#00FF00"));
                    break;
            default: break;
        }
        holder.historyBill.setText(Integer.toString(history.get(position).getBill()));
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView historyOrderId;
        public TextView historyRestaurantName;
        public TextView historyBill;
        public TextView historyStatus;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            historyRestaurantName = itemView.findViewById(R.id.history_restaurant_name);
            historyOrderId=itemView.findViewById(R.id.history_order_id);
            historyBill=itemView.findViewById(R.id.history_bill);
            historyStatus = itemView.findViewById(R.id.history_status);
        }
    }
}
