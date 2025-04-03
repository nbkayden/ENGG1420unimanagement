package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    @FXML
    private Label errorLabel; // Ensure this matches the fx:id in FXML

    @FXML
    private void handleManageSubjects() {
        try {
            // Load the subject view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/subject_view_admin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageCourses() {
        System.out.println("Manage Courses clicked");
    }

    @FXML
    private void handleManageStudents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/admin_manage_student_view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageFaculty() {
        System.out.println("Manage Faculty clicked");
    }

    @FXML
    private void handleManageEvents() {
        System.out.println("Manage Events clicked");
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