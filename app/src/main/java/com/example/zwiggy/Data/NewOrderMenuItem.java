package com.example.zwiggy.Data;

public class NewOrderMenuItem {
    private String itemName;
    private int quatity;
    private int price;

    public NewOrderMenuItem(String itemName, int quatity, int price) {
        this.itemName = itemName;
        this.quatity = quatity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

