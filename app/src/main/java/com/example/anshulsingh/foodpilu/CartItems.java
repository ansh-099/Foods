package com.example.anshulsingh.foodpilu;

import java.util.ArrayList;

public class CartItems {
    String RestaKey;
    String userKey;
    ArrayList<per_product> Products;

    public CartItems(String restaKey, String userKey, ArrayList<per_product> products) {
        RestaKey = restaKey;
        this.userKey = userKey;
        Products = products;
    }

    public String getRestaKey() {
        return RestaKey;
    }

    public void setRestaKey(String restaKey) {
        RestaKey = restaKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public ArrayList<per_product> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<per_product> products) {
        Products = products;
    }
}
