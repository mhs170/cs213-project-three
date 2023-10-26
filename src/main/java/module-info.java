module com.example.cs213projectthree {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs213projectthree to javafx.fxml;
    exports com.example.cs213projectthree;
}