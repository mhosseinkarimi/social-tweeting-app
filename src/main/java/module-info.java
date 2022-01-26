module com.example.twitter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.twitter to javafx.fxml;
    exports com.example.twitter;
}