module ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires core;
    requires persistence;

    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml, persistence, core, java.net.http;
}