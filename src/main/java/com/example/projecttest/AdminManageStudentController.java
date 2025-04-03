package com.example.projecttest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.geometry.Insets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class AdminManageStudentController {
    @FXML private TableView<Student> studentsTable;
    @FXML private Label messageLabel;

    private ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadStudents();
        studentsTable.setItems(students);
    }

    private void loadStudents() {
        students.clear();
        try (FileInputStream file = new FileInputStream("UMS_Data.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("Students");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                students.add(new Student(
                        row.getCell(0).getStringCellValue(), // ID
                        row.getCell(1).getStringCellValue(), // Name
                        row.getCell(2).getStringCellValue(), // Address
                        row.getCell(3).getStringCellValue(), // Phone
                        row.getCell(4).getStringCellValue(), // Email
                        row.getCell(5).getStringCellValue(), // Academic Level
                        row.getCell(6).getStringCellValue(), // Current Semester
                        row.getCell(11).getStringCellValue() // Password
                ));
            }
        } catch (IOException e) {
            messageLabel.setText("Error loading students: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddStudent() {
        // Create dialog for adding new student
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");
        dialog.setHeaderText("Enter student details");

        // Set up form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        ComboBox<String> levelCombo = new ComboBox<>(FXCollections.observableArrayList("Undergraduate", "Graduate"));
        TextField semesterField = new TextField();
        PasswordField passwordField = new PasswordField();

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Academic Level:"), 0, 5);
        grid.add(levelCombo, 1, 5);
        grid.add(new Label("Current Semester:"), 0, 6);
        grid.add(semesterField, 1, 6);
        grid.add(new Label("Password:"), 0, 7);
        grid.add(passwordField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Process result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Student(
                        idField.getText(),
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        levelCombo.getValue(),
                        semesterField.getText(),
                        passwordField.getText()
                );
            }
            return null;
        });

        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(student -> {
            try {
                // Add to Excel
                FileInputStream file = new FileInputStream("UMS_Data.xlsx");
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheet("Students");

                Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
                newRow.createCell(0).setCellValue(student.getId());
                newRow.createCell(1).setCellValue(student.getName());
                newRow.createCell(2).setCellValue(student.getAddress());
                newRow.createCell(3).setCellValue(student.getPhone());
                newRow.createCell(4).setCellValue(student.getEmail());
                newRow.createCell(5).setCellValue(student.getAcademicLevel());
                newRow.createCell(6).setCellValue(student.getCurrentSemester());
                newRow.createCell(11).setCellValue(student.getPassword());

                FileOutputStream outFile = new FileOutputStream("UMS_Data.xlsx");
                workbook.write(outFile);
                outFile.close();
                workbook.close();

                // Refresh table
                loadStudents();
                messageLabel.setText("Student added successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");

            } catch (IOException e) {
                messageLabel.setText("Error adding student: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleDeleteStudent() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Please select a student to delete");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Student");
        alert.setContentText("Are you sure you want to delete " + selected.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Remove from Excel
                FileInputStream file = new FileInputStream("UMS_Data.xlsx");
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheet("Students");

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row.getCell(0).getStringCellValue().equals(selected.getId())) {
                        sheet.removeRow(row);
                        break;
                    }
                }

                FileOutputStream outFile = new FileOutputStream("UMS_Data.xlsx");
                workbook.write(outFile);
                outFile.close();
                workbook.close();

                // Refresh table
                loadStudents();
                messageLabel.setText("Student deleted successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");

            } catch (IOException e) {
                messageLabel.setText("Error deleting student: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEditStudent(javafx.event.ActionEvent event) {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Please select a student to edit");
            return;
        }

        // Create dialog for editing student
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Edit Student");
        dialog.setHeaderText("Edit student details");

        // Set up form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField(selected.getId());
        TextField nameField = new TextField(selected.getName());
        TextField addressField = new TextField(selected.getAddress());
        TextField phoneField = new TextField(selected.getPhone());
        TextField emailField = new TextField(selected.getEmail());
        ComboBox<String> levelCombo = new ComboBox<>(FXCollections.observableArrayList("Undergraduate", "Graduate"));
        levelCombo.setValue(selected.getAcademicLevel());
        TextField semesterField = new TextField(selected.getCurrentSemester());
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Leave blank to keep current");

        idField.setDisable(true); // ID shouldn't be changed

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Academic Level:"), 0, 5);
        grid.add(levelCombo, 1, 5);
        grid.add(new Label("Current Semester:"), 0, 6);
        grid.add(semesterField, 1, 6);
        grid.add(new Label("New Password:"), 0, 7);
        grid.add(passwordField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Add buttons
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // Process result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return new Student(
                        idField.getText(),
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        levelCombo.getValue(),
                        semesterField.getText(),
                        passwordField.getText().isEmpty() ? selected.getPassword() : passwordField.getText()
                );
            }
            return null;
        });

        Optional<Student> result = dialog.showAndWait();
        result.ifPresent(student -> {
            try {
                // Update in Excel
                FileInputStream file = new FileInputStream("UMS_Data.xlsx");
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheet("Students");

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;

                    if (row.getCell(0).getStringCellValue().equals(student.getId())) {
                        row.getCell(1).setCellValue(student.getName());
                        row.getCell(2).setCellValue(student.getAddress());
                        row.getCell(3).setCellValue(student.getPhone());
                        row.getCell(4).setCellValue(student.getEmail());
                        row.getCell(5).setCellValue(student.getAcademicLevel());
                        row.getCell(6).setCellValue(student.getCurrentSemester());
                        if (!passwordField.getText().isEmpty()) {
                            row.getCell(11).setCellValue(student.getPassword());
                        }
                        break;
                    }
                }

                FileOutputStream outFile = new FileOutputStream("UMS_Data.xlsx");
                workbook.write(outFile);
                outFile.close();
                workbook.close();

                // Refresh table
                loadStudents();
                messageLabel.setText("Student updated successfully!");
                messageLabel.setStyle("-fx-text-fill: green;");

            } catch (IOException e) {
                messageLabel.setText("Error updating student: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) studentsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}