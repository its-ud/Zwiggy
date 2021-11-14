package com.example.zwiggy.UI.OwnerUI.AcceptedOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Adapter.AcceptedOrderAdapter;
import com.example.zwiggy.Adapter.PendingOrderAdapter;
import com.example.zwiggy.Data.PendingOrder;
import com.example.zwiggy.R;
import com.example.zwiggy.databinding.FragmentAcceptedordersBinding;

import java.util.ArrayList;

public class AcceptedOrdersFragment extends Fragment {

    private AcceptedOrdersViewModel notificationsViewModel;
    private FragmentAcceptedordersBinding binding;
    ArrayList<PendingOrder> acceptedOrders;
    RecyclerView rvAcceptedOrders;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(AcceptedOrdersViewModel.class);

        binding = FragmentAcceptedordersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        acceptedOrders = new ArrayList<PendingOrder>();
//        acceptedOrders.add("yash1 bansal");
//        acceptedOrders.add("Utkarsh1 chutiya");
//        acceptedOrders.add("Mota1 Bhadwa");

        rvAcceptedOrders = root.findViewById(R.id.rvAcceptedOrders);
        AcceptedOrderAdapter adapter = new AcceptedOrderAdapter(getContext(), acceptedOrders);
        rvAcceptedOrders.setAdapter(adapter);
        rvAcceptedOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

