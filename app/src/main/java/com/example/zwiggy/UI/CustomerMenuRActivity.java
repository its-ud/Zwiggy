package com.example.zwiggy.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zwiggy.Adapter.CustomerMenuAdapter;
import com.example.zwiggy.Adapter.OwnerMenuAdapter;
import com.example.zwiggy.Data.MenuItem;
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
    String appID = "hackit-qyzey",OwnerID;
    String LOG_TAG = CustomerMenuRActivity.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollectionOwner,mongoCollectionitem;
    ArrayList<MenuItem> ownerMenu;
    RecyclerView rvOwnerMenu;
    ArrayList<Document> itemArray;
    Button createNewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu_ractivity);
        createNewOrder=findViewById(R.id.create_order);
        createNewOrder.setOnClickListener(createNewOrderClick);
        app = new App(new AppConfiguration.Builder(appID).build());
        user = UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");
        mongoCollectionitem = mongoDatabase.getCollection("items");

        ownerMenu = new ArrayList<MenuItem>();

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

    View.OnClickListener createNewOrderClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}