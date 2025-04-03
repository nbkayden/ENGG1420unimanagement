package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileViewControllerStudent {
    @FXML private Label studentIdLabel;
    @FXML private Label nameLabel;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private Label emailLabel;
    @FXML private Label academicLevelLabel;
    @FXML private Label semesterLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private String currentStudentId;

    public void setStudentData(Student student) {
        this.currentStudentId = student.getId();
        studentIdLabel.setText(student.getId());
        nameLabel.setText(student.getName());
        addressField.setText(student.getAddress());
        phoneField.setText(student.getPhone());
        emailLabel.setText(student.getEmail());
        academicLevelLabel.setText(student.getAcademicLevel());
        semesterLabel.setText(student.getCurrentSemester());
    }

    @FXML
    private void handleSaveChanges() {
        try {
            // Open Excel file
            FileInputStream file = new FileInputStream("UMS_Data.xlsx");
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(2);

            // Find and update student record
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                if (row.getCell(0).getStringCellValue().equals(currentStudentId)) {
                    // Update editable fields
                    row.getCell(2).setCellValue(addressField.getText()); // Address
                    row.getCell(3).setCellValue(phoneField.getText());   // Phone

                    // Update password if changed
                    if (!passwordField.getText().isEmpty()) {
                        row.getCell(11).setCellValue(passwordField.getText()); // Password
                    }

                    break;
                }
            }

            // Save changes
            FileOutputStream outFile = new FileOutputStream("UMS_Data.xlsx");
            workbook.write(outFile);
            outFile.close();
            workbook.close();

            messageLabel.setText("Profile updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

        } catch (IOException e) {
            messageLabel.setText("Error saving changes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/student_dashboard.fxml"));
            Parent root = loader.load();

            // Pass student data back to dashboard
            StudentController controller = loader.getController();
            controller.setCurrentStudentId(currentStudentId);

            Stage stage = (Stage) studentIdLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}