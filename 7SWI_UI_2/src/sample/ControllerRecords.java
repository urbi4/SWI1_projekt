package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRecords implements Initializable {

    @FXML
    private TableView<?> table;

    @FXML
    private DatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.valueProperty().addListener((ov, oldValue, newValue) -> {
            dateSelected();
        });
    }

    private void dateSelected(){
        System.out.println("SELECTED");
    }

}
