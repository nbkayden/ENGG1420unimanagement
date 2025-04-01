package com.example.projecttest;

public class CoursesStudent {
    private final String courseCode;
    private final String courseName;
    private final String lectureTime;
    private final String teacherName;
    private final String location;

    public CoursesStudent(String courseCode, String courseName, String lectureTime,
                          String teacherName, String location) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lectureTime = lectureTime;
        this.teacherName = teacherName;
        this.location = location;
    }

    // Getters must exactly match property names in FXML
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getLectureTime() { return lectureTime; }
    public String getTeacherName() { return teacherName; }
    public String getLocation() { return location; }
}