module banking {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens banking to javafx.fxml;
    exports banking;
}