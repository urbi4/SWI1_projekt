module LastUpdateProject {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens sample;
    opens sample.database;
}