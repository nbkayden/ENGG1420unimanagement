package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private List<Student> students;
    private List<Faculty> faculties;

    public void initialize() {
        // Load data from Excel
        ExcelReader reader = new ExcelReader("UMS_Data.xlsx");
        students = reader.loadStudents();
        faculties = reader.loadFaculties();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin")) {
            if (password.equals("admin123")) {
                loadDashboard("admin_dashboard.fxml");
            } else {
                errorLabel.setText("Invalid admin password.");
            }
        } else {
            // Check if user is a student
            for (Student student : students) {
                if (student.getId().equals(username) && student.getPassword().equals(password)) {
                    loadDashboard("student_dashboard.fxml");
                    return;
                }
            }

            // Check if user is a faculty
            for (Faculty faculty : faculties) {
                if (faculty.getId().equals(username) && faculty.getPassword().equals(password)) {
                    loadDashboard("faculty_dashboard.fxml");
                    return;
                }
            }

            errorLabel.setText("Invalid username or password.");
        }
    }

    private void loadDashboard(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            //stage.setWidth(800); // Set width
            //stage.setHeight(600); // Set height
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}