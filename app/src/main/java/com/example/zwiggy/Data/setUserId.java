package com.example.zwiggy.Data;

import android.util.Log;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class setUserId {
    public static void setuserid(){
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Document> mongoCollection;
        String appID = "hackit-qyzey";
        User user;
        App app;
        Document res;
        String LOG_TAG="GET USER ID";
        user=UserDetail.getUser();
        app= new App(new AppConfiguration.Builder(appID).build());
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollection = mongoDatabase.getCollection("users");
        Document queryFilter = new Document().append("userId", UserDetail.getUid()).append("type", UserDetail.getType());
        mongoCollection.findOne(queryFilter).getAsync(result ->
        {
            if (result.isSuccess()) {
                String userid= result.get().getObjectId("_id").toString();
                String name=result.get().getString("Name");
                UserDetail.setName(name);
                UserDetail.setuserOwnerid(userid);
                Log.v(LOG_TAG, "USER ID FOUND and set");
            } else {
                Log.v(LOG_TAG, "User ID NOT FOUND");
            }
        });
    }
}
