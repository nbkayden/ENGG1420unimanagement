package com.example.projecttest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectController extends JFrame {
    @FXML
    private TableView<Subject> subjectTable; // TableView to display subjects

    private List<Subject> subjects = new ArrayList<>();
    public JTextField NameInput;
    public JTextField CodeInput;
    public JLabel FinishingLabel;
    public JButton AddingButton;
    public JButton EditingButton;
    public JButton DeletingButton;
    public JTextField SearchBar;
    public JLabel searchedName;
    public JLabel attachedCode;
    public JButton searchButton;

    //For edit_subjects
    public JTextField initialName;
    public JTextField initialCode;
    public JLabel newSubjectLabel;
    public JLabel newCodeLabel;
    public JTextField newName;
    public JTextField newCode;
    //End of edit_subjects variable
    public String SubjectInput;
    public String SubjectCode;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projecttest/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddSubject() {
        try {
            FXMLLoader adding = new FXMLLoader(getClass().getResource("/com/example/projecttest/manage_subjects.fxml"));
            Parent root = adding.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            AddingButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private boolean AddingSubjects() {
        for (Subject subject : subjects) {
            if (subject.getSubjectCode().equals(CodeInput.getText()) || subject.getSubjectName().equals(NameInput.getText())) {
                return false;
            }
        }
        SubjectInput = NameInput.getText();
        SubjectCode = CodeInput.getText();
        subjects.add(new Subject(SubjectCode, SubjectInput));
        FinishingLabel.setVisible(true);
        return true;
    }

    @FXML
    private void handleEditSubject() {
        try {
            FXMLLoader adding = new FXMLLoader(getClass().getResource("/com/example/projecttest/edit_subjects.fxml"));
            Parent root = adding.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private boolean EditingSubjects1() {
        for (Subject subject : subjects) {
            if (Objects.equals(initialCode.getText(), subject.getSubjectCode()) && Objects.equals(initialName.getText(), subject.getSubjectName())) {
                newSubjectLabel.setVisible(true);
                newName.setVisible(true);
                newCode.setVisible(true);
                newCodeLabel.setVisible(true);
                EditingButton.setVisible(true);
                return true;
            }
        }
        initialCode.setText("");
        initialName.setText("");
        return false;
    }

    @FXML
    private void EditingSubjects2() {
        for (Subject subject : subjects) {
            if (Objects.equals(initialCode.getText(), subject.getSubjectCode()) && Objects.equals(initialName.getText(), subject.getSubjectName())) {
                subject.setSubjectCode(newCode.getText());
                subject.setSubjectName(newName.getText());
            }
        }
    }

    @FXML
    private void handleDeletingSubject() {
        try {
            FXMLLoader adding = new FXMLLoader(getClass().getResource("/com/example/projecttest/manage_subjects.fxml"));
            Parent root = adding.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            DeletingButton.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void DeleteSubject() {
        for (Subject subject : subjects) {
            if (Objects.equals(CodeInput.getText(), subject.getSubjectCode()) && Objects.equals(NameInput.getText(), subject.getSubjectName())) {
                subjects.remove(subject);
            }
        }
    }

    @FXML
    private void handleViewSubjects() {
        try {
            FXMLLoader adding = new FXMLLoader(getClass().getResource("/com/example/projecttest/manage_subjects.fxml"));
            Parent root = adding.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            subjectTable.getItems().addAll(subjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SubjectSearch() {
        try {
            FXMLLoader adding = new FXMLLoader(getClass().getResource("/com/example/projecttest/search_results.fxml"));
            Parent root = adding.load();
            Stage stage = (Stage) subjectTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Subject subject : subjects) {
            if (subject.getSubjectName().toLowerCase().equals(SearchBar.getText().toLowerCase())) {
                searchedName.setText("Subject Name:" + subject.getSubjectName());
                attachedCode.setText("Subject Code:" + subject.getSubjectCode());
                searchButton.enable(true);
            }
        }
        if(searchedName.getText().equals("Subject Name:")) {
            searchedName.setText("Subject Does not Exist");
        }
    }
    @FXML
    private void deleteSearched(){
        for (Subject subject : subjects) {
            if (subject.getSubjectName().toLowerCase().equals(SearchBar.getText().toLowerCase())) {
                subjects.remove(subject);
            }
        }
    }
}