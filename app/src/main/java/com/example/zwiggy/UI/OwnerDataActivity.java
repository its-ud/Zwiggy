package com.example.zwiggy.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;

import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class OwnerDataActivity extends AppCompatActivity {

    EditText editRestaurantName;
    EditText editLocation;
    EditText editMinAmtPerOrder;
    String RestaurantName,Location;
    int MinAmnt=0;
    Button done;
    App app;
    User user;
    String appID = "hackit-qyzey";
    String LOG_TAG = OwnerDataActivity.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    String OwnerID;
    Document res;
    MongoCollection<Document> mongoCollectionUser,mongoCollectionOwner;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_data);

        editRestaurantName = findViewById(R.id.ownerRestaurantName);
        editLocation = findViewById(R.id.ownerRestaurantLocation);
        editMinAmtPerOrder =findViewById(R.id.ownerMinAmountPerOrder);
        progressBar = findViewById(R.id.progressOwnerDetails);
        progressBar.setVisibility(View.GONE);

        done = findViewById(R.id.ownerDone);

        app = new App(new AppConfiguration.Builder(appID).build());
        user=UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionUser = mongoDatabase.getCollection("users");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord(view);
                progressBar.setVisibility(View.VISIBLE);
                done.setEnabled(false);
                RestaurantName=editRestaurantName.getText().toString();
                Location=editLocation.getText().toString();
                MinAmnt= Integer.parseInt(editMinAmtPerOrder.getText().toString());
                findOwnerDetails();

            }
        });
    }
    void findOwnerDetails()
    {
        if(RestaurantName!=null && Location!=null && MinAmnt>0) {

            Document queryFilter = new Document().append("userId", UserDetail.getUid()).append("type", UserDetail.getType());
            mongoCollectionUser.findOne(queryFilter).getAsync(result ->
            {
                if (result.isSuccess()) {
                    res = result.get();
                    OwnerID = res.getObjectId("_id").toString();
                    addOwnerDetails();
                    Log.v(LOG_TAG, "broadcasted the object found");
                } else {
                    Log.v(LOG_TAG, "object document found not broadcasted");
                }
            });
        }
    }
    void addOwnerDetails()
    {
        ArrayList<Document> temp=new ArrayList<Document>();
        LatLng latLng = getLocationFromAddress(getApplicationContext(), Location);
        if(latLng!=null) {
            mongoCollectionOwner.insertOne(new Document("OwnerId", OwnerID).append("Name", RestaurantName)
                    .append("Loc", Location).append("userId", UserDetail.getUid()).append("Longitude", latLng.longitude)
                    .append("Latitude",latLng.latitude)
                    .append("Menu",temp)
                    .append("MinAmnt", MinAmnt)).getAsync(result -> {
                if (result.isSuccess()) {
                    Log.v(LOG_TAG, "owner Insertion is successful");
                    done.setEnabled(true);
                    Intent intent = new Intent(OwnerDataActivity.this, OwnerActivity.class);
                    startActivity(intent);
                    finish();
                }
                 else {
                     progressBar.setVisibility(View.GONE);
                     done.setEnabled(true);
                    Log.v(LOG_TAG, "INsertion was not successful" + result.getError().toString());
                }
            });
        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;

            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

    }
}