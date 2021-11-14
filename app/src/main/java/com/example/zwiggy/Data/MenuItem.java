package com.example.zwiggy.Data;

public class MenuItem {
    private String name;
    private int price;
    private String disc;
    int quantity;
    int itemAmnt;


    public MenuItem(String name, int price, String disc){
        this.name = name;
        this.price = price;
        this.disc = disc;
    }
//    public MenuItem(String name, int price,int,quant,int itemAmnt){
//        this.name = name;
//        this.price = price;
//        this.itemAmnt=itemAmnt;
//        this.quantity=
//    }
    public int getItemAmnt()
    {
        return this.itemAmnt;
    }
    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
