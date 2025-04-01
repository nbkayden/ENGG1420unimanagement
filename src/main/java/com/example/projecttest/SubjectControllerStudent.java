package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectControllerStudent {
    @FXML
    private TableView<Subject> subjectTable; // TableView to display subjects

    private List<Subject> subjects = new ArrayList<>();

    @FXML
    public void initialize() {
        // Load data from Excel
        loadSubjectsFromExcel();

        // Add data to TableView
        subjectTable.getItems().addAll(subjects);
    }

    private void loadSubjectsFromExcel() {
        try (FileInputStream file = new FileInputStream("UMS_Data.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet("Subjects");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String subjectCode = row.getCell(0).getStringCellValue();
                String subjectName = row.getCell(1).getStringCellValue();

                subjects.add(new Subject(subjectCode, subjectName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            // Load the admin dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/student_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}