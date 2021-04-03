package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import sample.database.*;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField inputName;
    @FXML
    private TextField input1;
    @FXML
    private TableView<TableDisplayStructure> table;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnName;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnSurname;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnPhoneNumber;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnPlateNumber;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnDate;
    @FXML
    private TableColumn<TableDisplayStructure, String> columnTime;
    @FXML
    private Button button;
    @FXML
    private Button removeButton;
    @FXML
    private Button edit;

    private Connection connection;

    @FXML
    private ObservableList<TableDisplayStructure> list = FXCollections.observableArrayList();

    private ObservableList<TableDisplayStructure> refreshList = FXCollections.observableArrayList();

    private static ObservableList<TableDisplayStructure> selectedPerson = FXCollections.observableArrayList();

    public static ObservableList<TableDisplayStructure> getSelectedPerson() {
        return selectedPerson;
    }

    public static void setSelectedPerson(ObservableList<TableDisplayStructure> selectedPerson) {
        Controller.selectedPerson = selectedPerson;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
//        ArrayList<Person> temp = DBSOperations.getList(connection);
//        for (Person person : temp) {
//            list.add(person);
//        }
        columnName.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("surname"));
        columnPlateNumber.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("plateNumber"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("phoneNumber"));
        columnDate.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("timeRange"));
        table.setItems(list);
    }

    public void changeSceneToReservation() throws Exception {
        SceneManager.changeScene("reservation.fxml");
    }

    public void closeButton(ActionEvent event) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void refresh() {
        refreshList.clear();
        ArrayList<TableDisplayStructure> temp = DBSOperations.getList(connection);
        for (TableDisplayStructure tds : temp) {
            refreshList.add(tds);
        }
        table.setItems(refreshList);
    }

    //TODO
    public void edit() throws Exception {
        ObservableList<TableDisplayStructure> personSelected;
        personSelected = table.getSelectionModel().getSelectedItems();
        if (personSelected.isEmpty()) return;
        selectedPerson.add(personSelected.get(0));
        setSelectedPerson(selectedPerson);
        changeSceneToReservation();
    }

    //TODO
    public void remove() {
        ObservableList<TableDisplayStructure> personSelected, allPersons;
        allPersons = table.getItems();
        personSelected = table.getSelectionModel().getSelectedItems();
        if (personSelected.isEmpty()) return;
        personSelected.forEach(allPersons::remove);
        list.remove(personSelected);
        //DBSOperations.remove(connection, personSelected.get(0));
    }
}

