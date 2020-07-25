package com.example.anshulsingh.foodpilu;

import java.util.ArrayList;

public class rest_menu {
   private ArrayList<per_product> stater;
   private ArrayList<per_product> mainCourse;
   private ArrayList<per_product> desserts;

   public rest_menu(){

   }
    public rest_menu(ArrayList<per_product> stater, ArrayList<per_product> mainCourse, ArrayList<per_product> desserts) {
        this.stater = stater;
        this.mainCourse = mainCourse;
        this.desserts = desserts;
    }

    public ArrayList<per_product> getStater() {
        return stater;
    }

    public void setStater(ArrayList<per_product> stater) {
        this.stater = stater;
    }

    public ArrayList<per_product> getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(ArrayList<per_product> mainCourse) {
        this.mainCourse = mainCourse;
    }

    public ArrayList<per_product> getDesserts() {
        return desserts;
    }

    public void setDesserts(ArrayList<per_product> desserts) {
        this.desserts = desserts;
    }
}
