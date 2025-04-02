package com.example.projecttest;

public class RegisteredEventStudent {
    private String eventCode;
    private String eventName;
    private String description;
    private String dateTime;
    private String location;

    public RegisteredEventStudent(String eventCode, String eventName, String description,
                           String dateTime, String location) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
    }

    // Getters
    public String getEventCode() { return eventCode; }
    public String getEventName() { return eventName; }
    public String getDescription() { return description; }
    public String getDateTime() { return dateTime; }
    public String getLocation() { return location; }
}