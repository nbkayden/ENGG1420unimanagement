package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisteredEventControllerStudent {
    @FXML
    private TableView<RegisteredEventStudent> eventsTable;

    private String currentStudentName;
    private List<RegisteredEventStudent> registeredEvents = new ArrayList<>();

    public void setStudentName(String studentName) {
        this.currentStudentName = studentName;
        try {
            loadRegisteredEvents();
            eventsTable.getItems().addAll(registeredEvents);
        } catch (Exception e) {
            showAlert("Error", "Failed to load events: " + e.getMessage());
        }
    }

    private void loadRegisteredEvents() throws Exception {
        try (FileInputStream file = new FileInputStream("UMS_Data.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet eventsSheet = workbook.getSheetAt(4);

            if (eventsSheet == null) {
                throw new Exception("Events sheet not found in Excel file");
            }

            for (Row row : eventsSheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Cell registeredStudentsCell = row.getCell(8);
                if (registeredStudentsCell != null) {
                    String registeredStudents = getCellValueAsString(registeredStudentsCell);
                    if (registeredStudents != null && registeredStudents.contains(currentStudentName)) {
                        registeredEvents.add(new RegisteredEventStudent(
                                getCellValueAsString(row.getCell(0)), // Event Code
                                getCellValueAsString(row.getCell(1)), // Event Name
                                getCellValueAsString(row.getCell(2)), // Description
                                getCellValueAsString(row.getCell(4)), // Date and Time
                                getCellValueAsString(row.getCell(3))  // Location
                        ));
                    }
                }
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int)cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/student_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) eventsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to return to dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}