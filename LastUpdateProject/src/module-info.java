module LastUpdateProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens sample.database;
    opens sample;
}