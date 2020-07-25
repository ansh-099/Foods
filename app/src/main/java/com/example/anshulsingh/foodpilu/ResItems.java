package com.example.anshulsingh.foodpilu;

import java.util.ArrayList;

public class ResItems {

      String name, timing, min_order, res_addr, res_phno, res_image, id;
       ArrayList<per_product> items;

   public  ResItems(){

   }

    public ResItems(String name, String timing, String min_order, String res_addr, String res_phno, String res_image, String id, ArrayList<per_product> items) {
        this.name = name;
        this.timing = timing;
        this.min_order = min_order;
        this.res_addr = res_addr;
        this.res_phno = res_phno;
        this.res_image = res_image;
        this.id = id;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getMin_order() {
        return min_order;
    }

    public void setMin_order(String min_order) {
        this.min_order = min_order;
    }

    public String getRes_addr() {
        return res_addr;
    }

    public void setRes_addr(String res_addr) {
        this.res_addr = res_addr;
    }

    public String getRes_phno() {
        return res_phno;
    }

    public void setRes_phno(String res_phno) {
        this.res_phno = res_phno;
    }

    public String getRes_image() {
        return res_image;
    }

    public void setRes_image(String res_image) {
        this.res_image = res_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<per_product> getItems() {
        return items;
    }

    public void setItems(ArrayList<per_product> items) {
        this.items = items;
    }
}
