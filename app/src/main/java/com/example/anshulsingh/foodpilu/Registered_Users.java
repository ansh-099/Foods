package com.example.anshulsingh.foodpilu;

public class Registered_Users {
    String name,no,password,email;

    Registered_Users(){

    }

    public Registered_Users(String name, String no, String password, String email) {
        this.name = name;
        this.no = no;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
