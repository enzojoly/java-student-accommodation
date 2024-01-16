module uwe.tae.sys {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens uwe.tae.sys.controller to javafx.fxml;
    opens uwe.tae.sys to javafx.fxml, javafx.graphics;
}
