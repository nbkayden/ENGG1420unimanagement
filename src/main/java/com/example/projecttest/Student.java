package com.example.projecttest;

public class Student {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String academicLevel;
    private String currentSemester;
    private String password;

    // Constructor
    public Student(String id, String name, String address, String phone,
                   String email, String academicLevel, String currentSemester,
                   String password) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.academicLevel = academicLevel;
        this.currentSemester = currentSemester;
        this.password = password;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAcademicLevel() { return academicLevel; }
    public String getCurrentSemester() { return currentSemester; }
    public String getPassword() { return password; }

    // Setters for editable fields
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPassword(String password) { this.password = password; }
}