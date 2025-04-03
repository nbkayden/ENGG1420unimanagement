package com.example.projecttest;

public class Faculty extends User {
    public Faculty(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    // Add getter methods for email and password
    public String getEmail() {
        return super.getEmail();
    }

    public String getId() {
        return super.getId();
    }

    public String getPassword() {
        return super.getPassword();
    }
}