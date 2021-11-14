package com.example.zwiggy.Data;

public class Restaurant {
    private String id;
    private String name;
    private String location;
    private int mminAmnt;

    public void setminAmnt(int minAmnt)
    {
        mminAmnt=minAmnt;
    }
    public int getMinAmnt()
    {
        return mminAmnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
