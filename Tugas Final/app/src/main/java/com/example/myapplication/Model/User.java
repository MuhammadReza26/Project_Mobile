package com.example.myapplication.Model;

public class User {
    private String name;
    private String username;

    public User(String name, String username) {
        this.name = name;
        this.username = username;

    }
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

}

