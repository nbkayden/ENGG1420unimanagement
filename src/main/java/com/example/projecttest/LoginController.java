package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private List<Student> students;
    private List<Faculty> faculties;

    @FXML
    public void initialize() {
        // Load data from Excel
        ExcelReader reader = new ExcelReader("UMS_Data.xlsx");
        students = reader.loadStudents();
        faculties = reader.loadFaculties();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals("admin")) {
            if (password.equals("admin123")) {
                loadDashboard("admin_dashboard.fxml");
            } else {
                errorLabel.setText("Invalid admin password.");
            }
            return;
        }

        // Check if user is a student
        for (Student student : students) {
            if (student.getId().equals(username) && student.getPassword().equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/student_dashboard.fxml"));
                    Parent root = loader.load();

                    // Get the controller and set the student ID
                    StudentController studentController = loader.getController();
                    studentController.setCurrentStudentId(student.getId());
                    studentController.setCurrentStudentName(student.getName()); // Add this method to StudentController

                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    errorLabel.setText("Error loading student dashboard");
                }
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

    private void loadDashboard(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading dashboard");
        }
    }
}