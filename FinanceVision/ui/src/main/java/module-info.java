module ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires core;
    requires persistence;

    opens ui to javafx.graphics, javafx.fxml, persistence, core;
}