package com.example.zwiggy.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Adapter.CreateNewOrderAdapter;
import com.example.zwiggy.Adapter.CustomerMenuAdapter;
import com.example.zwiggy.Adapter.OwnerMenuAdapter;
import com.example.zwiggy.Data.MenuItem;
import com.example.zwiggy.Data.NewOrderMenuItem;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;

import org.bson.BSON;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import org.bson.types.ObjectId;
public class CreateNewOrder extends AppCompatActivity {

    ArrayList<NewOrderMenuItem> newOrder;
    RecyclerView rvNewOrder;
    TextView newOrderId;
    TextView newOrderCustomerName;
    TextView newOrderRestaurantName;
    TextView newOrderBill;
    Button acceptOrder;
    Button rejectOrder;
    Button placeOrder;
    String Resname,Cusname="me",Cusid,Resid;
    int totalAmnt,orderState;


    App app;
    User user;
    String appID = "hackit-qyzey";
    String LOG_TAG = CreateNewOrder.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    String OwnerID;
    Document res;
    MongoCollection<Document> mongoCollectionUser,mongoCollectionOwner,mongoCollectionOrder;
    ArrayList<Document> itemArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_order);

        newOrder = new ArrayList<NewOrderMenuItem>();
        newOrderId=findViewById(R.id.new_order_id);
        newOrderCustomerName=findViewById(R.id.new_order_customer_name);
        newOrderRestaurantName=findViewById(R.id.new_order_restaurant_name);
        newOrderBill=findViewById(R.id.total_bill);
        acceptOrder=findViewById(R.id.accept_order);
        rejectOrder=findViewById(R.id.reject_order);
        placeOrder=findViewById(R.id.place_order);

        newOrderId.setText(UserDetail.getMorderId());
        app = new App(new AppConfiguration.Builder(appID).build());
        user=UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionUser = mongoDatabase.getCollection("users");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");
        mongoCollectionOrder = mongoDatabase.getCollection("orders");

        rvNewOrder = findViewById(R.id.rvCreateNewOrder);
        itemArray=new ArrayList<Document>();
        Log.v(LOG_TAG,"order id before search "+UserDetail.getMorderId());
        ObjectId objectId= new ObjectId(UserDetail.getMorderId());
        Document fqueryFilter = new Document("_id",objectId);
        mongoCollectionOrder.findOne(fqueryFilter).getAsync(result ->
        {
            if (result.isSuccess()) {

                Log.v(LOG_TAG, "order items  found  from id "+ result.get().getObjectId("_id").toString());

                Document fres = result.get();
                Cusname=fres.getString("Cus");
                Cusid=fres.getString("CusId");
                Resname=fres.getString("Res");
                Resid=fres.getString("ResId");
                orderState=fres.getInteger("State");
                totalAmnt=fres.getInteger("Total");

                newOrderCustomerName.setText(Cusname);
                newOrderRestaurantName.setText(Resname);
                newOrderBill.setText("Rs. "+ Integer.toString(totalAmnt));
                itemArray = (ArrayList<Document>) fres.get("Items");

                for (int i = 0; i < itemArray.size(); i++)
                {
                    newOrder.add(new NewOrderMenuItem(itemArray.get(i).getString("Name"),
                            itemArray.get(i).getInteger("quantity")
                    ,itemArray.get(i).getInteger("price")));
                }
                CreateNewOrderAdapter adapter = new CreateNewOrderAdapter(this,newOrder);
                rvNewOrder.setAdapter(adapter);
                rvNewOrder.setLayoutManager(new LinearLayoutManager(this));


            } else {
                Log.v(LOG_TAG, "order items not found from id");
            }
        });




        setButtons();

        acceptOrder.setOnClickListener(acceptOrderClick);
        rejectOrder.setOnClickListener(rejectOrderClick);
        placeOrder.setOnClickListener(placeOrderClick);
    }
    void setButtons(){
        switch (UserDetail.getIntentStatus()){
            case 0:acceptOrder.setVisibility(View.GONE);
                rejectOrder.setVisibility(View.GONE);
                placeOrder.setVisibility(View.VISIBLE);
                break;
            case 1:if(UserDetail.getType()==0) {
                acceptOrder.setVisibility(View.GONE);
                rejectOrder.setVisibility(View.GONE);
                placeOrder.setVisibility(View.GONE);
            }
            else{
                acceptOrder.setVisibility(View.VISIBLE);
                rejectOrder.setVisibility(View.VISIBLE);
                placeOrder.setVisibility(View.GONE);
            }
                break;
            case 2:acceptOrder.setVisibility(View.GONE);
                rejectOrder.setVisibility(View.GONE);
                placeOrder.setVisibility(View.GONE);
                break;
            case 3:acceptOrder.setVisibility(View.GONE);
                rejectOrder.setVisibility(View.GONE);
                placeOrder.setVisibility(View.GONE);
                break;
            default: break;
        }
    }
    View.OnClickListener acceptOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    View.OnClickListener rejectOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    View.OnClickListener  placeOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}