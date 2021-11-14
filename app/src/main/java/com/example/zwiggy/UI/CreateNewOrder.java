package com.example.zwiggy.UI;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Adapter.CreateNewOrderAdapter;
import com.example.zwiggy.Adapter.CustomerMenuAdapter;
import com.example.zwiggy.Data.MenuItem;
import com.example.zwiggy.Data.NewOrderMenuItem;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;

import org.bson.Document;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class CreateNewOrder extends AppCompatActivity {

    ArrayList<NewOrderMenuItem> newOrder;
    RecyclerView rvNewOrder;
    TextView newOrderId;
    TextView newOrderCustomerName;
    TextView newOrderRestaurantName;
    TextView newOrderBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_order);

        newOrder = new ArrayList<NewOrderMenuItem>();
        newOrderId=findViewById(R.id.new_order_id);
        newOrderCustomerName=findViewById(R.id.new_order_customer_name);
        newOrderRestaurantName=findViewById(R.id.new_order_restaurant_name);
        newOrderBill=findViewById(R.id.total_bill);

        rvNewOrder = findViewById(R.id.rvCreateNewOrder);
        CreateNewOrderAdapter adapter = new CreateNewOrderAdapter(this,newOrder);
        rvNewOrder.setAdapter(adapter);
        rvNewOrder.setLayoutManager(new LinearLayoutManager(this));

    }
}