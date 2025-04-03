package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class StudentController {
    @FXML
    private Label errorLabel;
    private String currentStudentId;
    private String currentStudentName;// Add this field to store student ID
    private static String preservedStudentName;
    private static String preservedStudentId;

    @FXML
    public void initialize() {
        if (preservedStudentName != null) {
            this.currentStudentName = preservedStudentName;
        }// Rest of initialization
        if (preservedStudentId != null) {
            this.currentStudentId = preservedStudentId;
        }
    }

    // Add this method to set the student ID
    public void setCurrentStudentId(String studentId) {
        this.currentStudentId = studentId;
        preservedStudentId = studentId;
    }
    public void setCurrentStudentName(String studentName){
        this.currentStudentName = studentName;
        preservedStudentName = studentName; // Back up to static variable
    }

    @FXML
    private void handleViewProfile() {
        try {
            // Load student data from Excel
            Student student = loadStudentData(currentStudentId);

            // Load profile view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/profile_view_student.fxml"));
            Parent root = loader.load();

            // Pass student data to controller
            ProfileViewControllerStudent controller = loader.getController();
            controller.setStudentData(student);

            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Student loadStudentData(String studentId) throws IOException {
        try (FileInputStream file = new FileInputStream("UMS_Data.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(2);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                if (row.getCell(0).getStringCellValue().equals(studentId)) {
                    return new Student(
                            row.getCell(0).getStringCellValue(), // ID
                            row.getCell(1).getStringCellValue(), // Name
                            row.getCell(2).getStringCellValue(), // Address
                            row.getCell(3).getStringCellValue(), // Phone
                            row.getCell(4).getStringCellValue(), // Email
                            row.getCell(5).getStringCellValue(), // Academic Level
                            row.getCell(6).getStringCellValue(), // Current Semester
                            row.getCell(11).getStringCellValue() // Password
                    );
                }
            }
        }
        return null;
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

    /*@FXML
    private void handleViewGrades() {
        System.out.println("View Grades clicked");
    }*/

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
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}