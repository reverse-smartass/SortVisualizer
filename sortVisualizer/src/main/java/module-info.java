module org.example.sortvisualizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.sortvisualizer to javafx.fxml;
    exports org.example.sortvisualizer;
}