package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentController {
    @FXML
    private Label errorLabel;
    private String currentStudentId;
    private String currentStudentName;// Add this field to store student ID
    private static String preservedStudentName;

    @FXML
    public void initialize() {
        if (preservedStudentName != null) {
            this.currentStudentName = preservedStudentName;
        }
        // Rest of initialization
    }

    // Add this method to set the student ID
    public void setCurrentStudentId(String studentId) {
        this.currentStudentId = studentId;
    }
    public void setCurrentStudentName(String studentName){
        this.currentStudentName = studentName;
        preservedStudentName = studentName; // Back up to static variable
    }

    @FXML
    private void handleViewProfile() {
        System.out.println("View Profile clicked");
    }

    @FXML
    private void handleViewCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/courses_view_student.fxml"));
            Parent root = loader.load();

            CoursesControllerStudent controller = loader.getController();
            controller.setStudentId(currentStudentId);

            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading courses view");
        }
    }

    @FXML
    private void handleViewGrades() {
        System.out.println("View Grades clicked");
    }

    @FXML
    private void handleRegisteredEvents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/event_view_student.fxml"));
            Parent root = loader.load();

            RegisteredEventControllerStudent controller = loader.getController();
            controller.setStudentName(currentStudentName);

            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load events view: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) errorLabel.getScene().getWindow(); // Use errorLabel to get the scene
            stage.setScene(new Scene(root));
            //stage.setWidth(800); // Set width
            //stage.setHeight(600); // Set height
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}