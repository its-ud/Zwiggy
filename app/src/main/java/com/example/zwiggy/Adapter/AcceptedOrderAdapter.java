package com.example.zwiggy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Data.PendingOrder;
import com.example.zwiggy.R;
import com.example.zwiggy.UI.CreateNewOrder;

import java.util.ArrayList;

public class AcceptedOrderAdapter extends RecyclerView.Adapter<AcceptedOrderAdapter.AcceptedOrdersViewHolder> {

    ArrayList<PendingOrder> mAccepted;
    Context mContext;

    public AcceptedOrderAdapter(Context context, ArrayList<PendingOrder> accepted){
        mContext = context;
        mAccepted = accepted;
    }
    @NonNull
    @Override
    public AcceptedOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fragment_acceptedorder_list_item, parent, false);
        AcceptedOrdersViewHolder viewHolder = new AcceptedOrdersViewHolder(contactView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull AcceptedOrdersViewHolder holder, int position) {
        PendingOrder pending =mAccepted.get(position);
        holder.pendingOrderId.setText(pending.getOrderId());
        holder.pendingBill.setText(Integer.toString(pending.getBill()));
        holder.pendingCustomerName.setText(pending.getCustomerName());
    }

    @Override
    public int getItemCount() {
        return mAccepted.size();
    }

    public class AcceptedOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView pendingOrderId;
        public TextView pendingCustomerName;
        public TextView pendingBill;

        public AcceptedOrdersViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pendingOrderId = (TextView) itemView.findViewById(R.id.pending_order_id);
            pendingCustomerName = (TextView) itemView.findViewById(R.id.customer_name_pending);
            pendingBill = (TextView) itemView.findViewById(R.id.pending_bill);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, CreateNewOrder.class);
            mContext.startActivity(intent);
        }
    }
}