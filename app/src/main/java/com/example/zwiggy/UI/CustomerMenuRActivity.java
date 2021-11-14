package com.example.zwiggy.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zwiggy.Adapter.CustomerMenuAdapter;
import com.example.zwiggy.Adapter.OwnerMenuAdapter;
import com.example.zwiggy.Data.MenuItem;
import com.example.zwiggy.Data.ResDetail;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;
import com.example.zwiggy.UI.OwnerUI.OwnerMenu.OwnerMenuFragment;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class CustomerMenuRActivity extends AppCompatActivity {

    App app;
    User user;
    String appID = "hackit-qyzey";
    String LOG_TAG = CustomerMenuRActivity.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollectionOwner,mongoCollectionitem,mongoCollectionOrder;
    ArrayList<MenuItem> ownerMenu;
    RecyclerView rvOwnerMenu;
    ArrayList<Document> itemArray;
    Button createNewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu_ractivity);


        app = new App(new AppConfiguration.Builder(appID).build());
        user = UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");
        mongoCollectionitem = mongoDatabase.getCollection("items");
        mongoCollectionOrder = mongoDatabase.getCollection("orders");

        ownerMenu = new ArrayList<MenuItem>();

        createNewOrder=findViewById(R.id.create_order);
        createNewOrder.setOnClickListener(createNewOrderClick);
        rvOwnerMenu = findViewById(R.id.rvCustomerMenu);
        getItems();


    }
    public void getItems(){
        Document fqueryFilter = new Document("OwnerId", UserDetail.getcusresId());
        mongoCollectionOwner.findOne(fqueryFilter).getAsync(result ->
        {
            if (result.isSuccess()) {
                Document fres = result.get();
                itemArray = (ArrayList<Document>) fres.get("Menu");
                Log.i("data",Integer.toString( itemArray.size()));
                for (int i = 0; i < itemArray.size(); i++)
                {
                    ownerMenu.add(new MenuItem(itemArray.get(i).getString("Name"),
                            itemArray.get(i).getInteger("Price"), itemArray.get(i).getString("Desc")));
                }
                CustomerMenuAdapter adapter = new CustomerMenuAdapter(this, ownerMenu);
                rvOwnerMenu.setAdapter(adapter);
                rvOwnerMenu.setLayoutManager(new LinearLayoutManager(this));

                Log.v(LOG_TAG, "dishes of restaurants found ");
            } else {
                Log.v(LOG_TAG, "dishes of restaurants NOT found");
            }
        });
    }
    ArrayList<Document> orderItems;
    int totalAmnt=0;
    String orderid;
    View.OnClickListener createNewOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            orderItems=new ArrayList<Document>();
            for(int i=0;i<ownerMenu.size();i++){
                int quantity=ownerMenu.get(i).getQuantity();
                String name=ownerMenu.get(i).getName();
                int price=ownerMenu.get(i).getPrice();
                Log.i("info", name+" "+quantity);
                if(quantity>0)
                {
                    int itemAmount=quantity*price;
                    totalAmnt=totalAmnt+itemAmount;
                    Document temp=new Document().append("Name",name)
                            .append("quantity",quantity)
                            .append("price",price);
                    orderItems.add(temp);
                }
            }
            if(totalAmnt>= ResDetail.getResminAmnt())
            {
                Document toadd=new Document("Res",ResDetail.getResname())
                        .append("ResId",ResDetail.getResownerid())
                        .append("CusId",UserDetail.getuserOwnerid())
                        .append("Cus",UserDetail.getName())
                        .append("Items",orderItems)
                        .append("Total",totalAmnt)
                        .append("State",0);

                mongoCollectionOrder.insertOne(toadd).getAsync(result -> {
                    if (result.isSuccess()) {
                        orderid= result.get().getInsertedId().asObjectId().getValue().toString();
                        UserDetail.setMorderId(orderid);
                        UserDetail.setIntentStatus(0);
                        Log.v(LOG_TAG, "order Insertion is successful  "+orderid);
                        Intent intent = new Intent(CustomerMenuRActivity.this, CreateNewOrder.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Log.v(LOG_TAG, "order INsertion was not successful" + result.getError().toString());
                    }
                });

            }
            else{
                Toast.makeText(CustomerMenuRActivity.this,"Billing Amount is bellow par", Toast.LENGTH_SHORT).show();
            }
        }
    };
}