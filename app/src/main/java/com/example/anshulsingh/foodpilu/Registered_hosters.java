package com.example.anshulsingh.foodpilu;

public class Registered_hosters {
    String id,name,phone_no,password,image,category,location,type;

    public Registered_hosters(String id, String name, String phone_no, String password, String image, String category, String location, String type) {
        this.id = id;
        this.name = name;
        this.phone_no = phone_no;
        this.password = password;
        this.image = image;
        this.category = category;
        this.location = location;
        this.type = type;
    }
    Registered_hosters(){

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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
