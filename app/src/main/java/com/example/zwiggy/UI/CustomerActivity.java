package com.example.zwiggy.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zwiggy.Adapter.CustomerAdapter;
import com.example.zwiggy.Adapter.PendingOrderAdapter;


import com.example.zwiggy.Data.DocOb;
import com.example.zwiggy.Data.UserDetail;

import com.example.zwiggy.Data.Restaurant;

import com.example.zwiggy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import org.bson.Document;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class CustomerActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    ArrayList<Restaurant> restaurants;
    RecyclerView rvRestaurants;
    double customerLat=0, customerLong=0;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollectionOwner;
    String appID = "hackit-qyzey";
    User user;
    App app;
//    ArrayList<Restaurant> itemArray;
    String LOG_TAG=CustomerActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        app = new App(new AppConfiguration.Builder(appID).build());
        user = UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        Log.i("location 1 ",customerLat+""+customerLong);
        configureToolbar();
        restaurants = new ArrayList<Restaurant>();
        Document queryFilter = new Document();
//        itemArray = new ArrayList<Restaurant>();
        RealmResultTask<MongoCursor<Document>> findTask = mongoCollectionOwner.find(queryFilter).iterator();
        findTask.getAsync(task ->
        {
            if (task.isSuccess()) {
                MongoCursor<Document> result = task.get();
                while (result.hasNext()) {
                    Document curDoc = result.next();
                    try {
                        Log.i("location 1 ",customerLat+""+customerLong);
                       double resLat=curDoc.getDouble("Latitude");
                       double resLong=curDoc.getDouble("Longitude");
                       double cusLat=customerLat;
                       double cusLong=customerLong;
                       double dist= getDistance(resLat,resLong,cusLat,cusLong);
                       if(dist<=3000.0)
                       {
                           Restaurant restaurant=new Restaurant();
                           restaurant.setId(curDoc.getString("OwnerId"));
                           restaurant.setLocation(curDoc.getString("Loc"));
                           restaurant.setName(curDoc.getString("Name"));
                           restaurant.setminAmnt(curDoc.getInteger("MinAmnt"));
                           restaurants.add(restaurant);
                           Log.i(LOG_TAG,"restaurant in range "+ curDoc.getString("Name"));
                       }
                    } catch (Exception e) {
                        Log.v(LOG_TAG, "error in finding range rest", e);
                    }
                }
                rvRestaurants = findViewById(R.id.rvCustomer);
                CustomerAdapter adapter = new CustomerAdapter(this, restaurants);
                rvRestaurants.setAdapter(adapter);
                rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
                Log.v(LOG_TAG, "successfully found the restaurant in range");
            } else {
                Log.v(LOG_TAG, "Restaurant not found" + task.getError().toString());
            }

        });



    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            customerLat=location.getLatitude();
                            customerLong=location.getLongitude();
                            Log.i("location", customerLat+" "+customerLong);

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            customerLong=mLastLocation.getLongitude();
            customerLat=mLastLocation.getLatitude();

        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
}

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    double getDistance(double l1, double l2, double l3, double l4){
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(l1);
        startPoint.setLongitude(l2);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(l3);
        endPoint.setLongitude(l4);
        return  startPoint.distanceTo(endPoint);
    }
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.customer_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Zwiggy");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulist2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            String appID = "hackit-qyzey";
            App app = new App(new AppConfiguration.Builder(appID).build());
            app.currentUser().logOut();
            finish();
        }
        if(item.getItemId()==R.id.order_history){
            Intent intent = new Intent(CustomerActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}