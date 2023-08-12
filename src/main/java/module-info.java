module com.example.javaproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.javaproj to javafx.fxml;
    exports com.example.javaproj;
}