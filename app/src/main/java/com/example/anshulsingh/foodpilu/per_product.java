package com.example.anshulsingh.foodpilu;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class per_product implements Serializable {
    String productName;
    Integer rate, type;


    public per_product() {

    }


    public per_product(String productName, Integer rate, Integer type) {
        this.productName = productName;
        this.rate = rate;
        this.type = type;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

}