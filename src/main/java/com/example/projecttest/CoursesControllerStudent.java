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

public class CoursesControllerStudent {
    @FXML
    private TableView<CoursesStudent> coursesTable;

    private String currentStudentId;
    private List<CoursesStudent> CoursesStudents = new ArrayList<>();

    public void setStudentId(String studentId) {
        this.currentStudentId = studentId;
        loadCoursesStudents();
        coursesTable.getItems().addAll(CoursesStudents);
    }

    private void loadCoursesStudents() {
        try (FileInputStream file = new FileInputStream("UMS_Data.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);

            // 1. Get student's enrolled subjects
            Sheet studentsSheet = workbook.getSheetAt(2);
            List<String> studentSubjects = new ArrayList<>();

            for (Row row : studentsSheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Cell idCell = row.getCell(0);
                if (idCell != null && getCellValue(idCell).equals(currentStudentId)) {
                    Cell subjectsCell = row.getCell(8);
                    if (subjectsCell != null) {
                        String subjects = getCellValue(subjectsCell);
                        for (String subject : subjects.split(",")) {
                            studentSubjects.add(subject.trim());
                        }
                    }
                    break;
                }
            }

            // 2. Get matching courses
            Sheet coursesSheet = workbook.getSheetAt(1);
            for (Row row : coursesSheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Cell subjectCodeCell = row.getCell(2);
                if (subjectCodeCell != null && studentSubjects.contains(getCellValue(subjectCodeCell))) {
                    CoursesStudents.add(new CoursesStudent(
                            getCellValue(row.getCell(0)), // Course Code
                            getCellValue(row.getCell(1)), // Course Name
                            getCellValue(row.getCell(5)), // Lecture Time
                            getCellValue(row.getCell(8)), // Teacher
                            getCellValue(row.getCell(7))  // Location
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to safely get cell value as String
    private String getCellValue(Cell cell) {
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
            default:
                return "";
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/student_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) coursesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}