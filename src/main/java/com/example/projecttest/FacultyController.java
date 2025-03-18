package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FacultyController {
    @FXML
    private Label errorLabel; // Ensure this matches the fx:id in FXML

    @FXML
    private void handleViewProfile() {
        System.out.println("View Profile clicked");
    }

    @FXML
    private void handleViewCourses() {
        System.out.println("View Assigned Courses clicked");
    }

    @FXML
    private void handleViewStudents() {
        System.out.println("View Student Lists clicked");
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