package com.example.projecttest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(2);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Check if cells exist before accessing them
                String id = getCellValue(row, 0);
                String name = getCellValue(row, 1);
                String email = getCellValue(row, 4);
                String password = getCellValue(row, 11);

                if (id != null && name != null && email != null && password != null) {
                    students.add(new Student(id, name, email, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Faculty> loadFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(3);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Check if cells exist before accessing them
                String id = getCellValue(row, 0);
                String name = getCellValue(row, 1);
                String email = getCellValue(row, 4);
                String password = getCellValue(row, 7);

                if (id != null && name != null && email != null && password != null) {
                    faculties.add(new Faculty(id, name, email, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    // Helper method to safely get cell value
    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return null; // Return null if the cell does not exist
        }
        return cell.getStringCellValue();
    }
}