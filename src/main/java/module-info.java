module com.example.projecttest {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.example.projecttest to javafx.fxml;
    exports com.example.projecttest;
}