package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.database.*;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableController implements Initializable {

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
    @FXML
    private DatePicker datePicker;

    private Connection connection;

    private ArrayList<Order> listOfOrders;

    private ObservableList<TableDisplayStructure> listOfTDS = FXCollections.observableArrayList();

    private static Order selectedOrder;

    public static Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder() {
        ObservableList<TableDisplayStructure> tds = table.getSelectionModel().getSelectedItems();
        if (tds.size() != 1){
            return;
        }
        int index = listOfTDS.indexOf(tds.get(0));
        if (index < 0){
            return;
        }
        TableController.selectedOrder = listOfOrders.get(index);
        getSelectedOrder().setProblems(DBSOperations.getProblems(connection, getSelectedOrder().getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
//        ArrayList<Person> temp = DBSOperations.getList(connection);
//        for (Person person : temp) {
//            list.add(person);
//        }
        listOfOrders = new ArrayList<>();
        columnName.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("surname"));
        columnPlateNumber.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("plateNumber"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("phoneNumber"));
        columnDate.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<TableDisplayStructure, String>("timeRange"));
    }

    public void changeSceneToReservation() throws Exception {
        SceneManager.changeScene("reservation.fxml");
    }

    public void refresh() {
        listOfTDS.clear();
        LocalDate date = datePicker.getValue();
        if (date == null){
            return;
        }
        listOfOrders = DBSOperations.getList(connection, date);
        ArrayList<TableDisplayStructure> listOfTDS = makeTDSList(listOfOrders);
        for (TableDisplayStructure tds : listOfTDS) {
            this.listOfTDS.add(tds);
        }
        table.setItems(this.listOfTDS);
    }

    public void edit() throws Exception {
        setSelectedOrder();
        if (selectedOrder == null){
            return;
        }
        changeSceneToReservation();
    }

    //TODO
    public void remove() {
        ObservableList<TableDisplayStructure> personSelected, allPersons;
        allPersons = table.getItems();
        personSelected = table.getSelectionModel().getSelectedItems();
        if (personSelected.isEmpty()) return;
        personSelected.forEach(allPersons::remove);
        //list.remove(personSelected);
        //DBSOperations.remove(connection, personSelected.get(0));
    }

    private ArrayList<TableDisplayStructure> makeTDSList(ArrayList<Order> orders){
        ArrayList<TableDisplayStructure> export = new ArrayList<>();
        for (Order order : orders) {
            export.add(new TableDisplayStructure(order));
        }
        return export;
    }
}

