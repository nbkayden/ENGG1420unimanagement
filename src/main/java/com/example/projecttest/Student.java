package com.example.projecttest;

public class Student extends User {
    public Student(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    // Add getter methods for email and password
    public String getEmail() {
        return super.getEmail(); // Assuming the email field is in the parent User class
    }

    public String getId(){
        return super.getId();
    }

    public String getPassword() {
        return super.getPassword(); // Assuming the password field is in the parent User class
    }
}