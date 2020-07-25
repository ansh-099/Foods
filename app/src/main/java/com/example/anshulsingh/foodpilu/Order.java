package com.example.anshulsingh.foodpilu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Order {
    String resturantID, Address,resturantName;
    ArrayList<per_product> items;
    Integer cost;

    public Order(){

    }

    public Order(String resturantID, String address, String resturantName, ArrayList<per_product> items, Integer cost) {
        this.resturantID = resturantID;
        Address = address;
        this.resturantName = resturantName;
        this.items = items;
        this.cost = cost;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getResturantID() {
        return resturantID;
    }

    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public ArrayList<per_product> getItems() {
        return items;
    }

    public void setItems(ArrayList<per_product> items) {
        this.items = items;
    }
}
