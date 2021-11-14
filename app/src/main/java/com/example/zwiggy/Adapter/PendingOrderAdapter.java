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


public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.PendingOrdersViewHolder> {

    ArrayList<PendingOrder> mPendings;
    Context mContext;

    public PendingOrderAdapter(Context context, ArrayList<PendingOrder> pendings){
        mContext = context;
        mPendings = pendings;
    }
    @NonNull
    @Override
    public PendingOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fragment_pendingorders_list_item, parent, false);
        PendingOrdersViewHolder viewHolder = new PendingOrdersViewHolder(contactView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull PendingOrdersViewHolder holder, int position) {
        PendingOrder pending =mPendings.get(position);
        holder.pendingOrderId.setText(pending.getOrderId());
        holder.pendingBill.setText(Integer.toString(pending.getBill()));
        holder.pendingCustomerName.setText(pending.getCustomerName());

    }

    @Override
    public int getItemCount() {
        return mPendings.size();
    }

    public class PendingOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView pendingOrderId;
        public TextView pendingCustomerName;
        public TextView pendingBill;

        public PendingOrdersViewHolder(View itemView) {
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
