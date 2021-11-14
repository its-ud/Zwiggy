package com.example.zwiggy.Data;

import io.realm.mongodb.User;

public class UserDetail {
    private static User muser;
    private static String emailId;
    private static int mtype;
    private static String muid;
    private static String mcusresId;
    private static String mname;
    private static String muserOwnerid;
    private static String morderId;
    private static int intentStatus;

    public static String getMorderId()
    {
        return morderId;
    }
    public static void setMorderId(String orderId)
    {
        morderId=orderId;
    }
    public static int getIntentStatus()
    {
        return intentStatus;
    }
    public static void setIntentStatus(int x)
    {
        intentStatus=x;
    }


    public static String getUid() {
        return muid;
    }
    public static String getcusresId() {
        return mcusresId;
    }
    public static void setuserOwnerid(String userOwnerid) {
        muserOwnerid = userOwnerid;
    }
    public static String getuserOwnerid() {
        return muserOwnerid ;
    }
    public static void setmUid(String uid) {
        muid = uid;
    }
    public static void setmcusresId(String cusresId) {
        mcusresId = cusresId;
    }
    public static void setUser(User user) {
        muser=user;
    }
    public static void setEmailId(String emailid) {
        emailId=emailid;
    }
    public static void setType(int type){
        mtype = type;
    }
    public static String getEmailId() {
        return emailId;
    }
    public static int getType() {
        return mtype;
    }
    public static User getUser() {
        return muser;
    }
    public static String getName(){
        return mname;
    }
    public static void setName(String name){
        mname=name;
    }
}
