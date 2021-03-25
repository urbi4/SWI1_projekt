package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextField inputName;
    @FXML
    private TextField input1;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, String> columnName;
    @FXML
    private TableColumn<Person, String> columnSurname;
    @FXML
    private TableColumn<Person, String> columnAddress;
    @FXML
    private TableColumn<Person, Integer> columnPhone;
    @FXML
    private TableColumn<Person, String> columnDate;
    @FXML
    private TableColumn<Person, String> columnTime;
    @FXML
    private Button button;
    @FXML
    private Button removeButton;
    @FXML
    private Button edit;

    private Connection connection;

    private ObservableList<Person> list = FXCollections.observableArrayList();

    private ObservableList<Person> refreshList = FXCollections.observableArrayList();

    private static ObservableList<Person> selectedPerson = FXCollections.observableArrayList();

    public static ObservableList<Person> getSelectedPerson() {
        return selectedPerson;
    }

    public static void setSelectedPerson(ObservableList<Person> selectedPerson) {
        Controller.selectedPerson = selectedPerson;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
        ArrayList<Person> temp = DBOperations.getList(connection);
        for (Person person : temp) {
            list.add(person);
        }
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person,String>("surname"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Person,String>("address"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person,Integer>("phone"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Person,String>("date"));
        columnTime.setCellValueFactory(new PropertyValueFactory<Person,String>("time"));
        table.setItems(list);
    }

    public void changeScenes() throws Exception {
        Main m = new Main();
        m.changeScene("reservation.fxml");
    }

    public void changeScenes1() throws Exception {
        Main m = new Main();
        m.changeScene("edit.fxml");
    }

    public void closeButton(ActionEvent event){
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void refresh(){
        refreshList.clear();
        ObservableList<Person> result = FXCollections.observableArrayList();
        ArrayList<Person> temp = DBOperations.getList(connection);
        for (Person person : temp) {
            refreshList.add(person);
        }
        if(list.size() < refreshList.size()){
            Person person = refreshList.get(refreshList.size()-1);
            result.add(person);
            for(Person person1 : list){
                result.add(person1);
            }
            table.setItems(result);
        }
        else return;
    }

    public void edit() throws Exception {
        ObservableList<Person> personSelected;
        personSelected = table.getSelectionModel().getSelectedItems();
        if (personSelected.isEmpty()) return;
        selectedPerson.add(personSelected.get(0));
        setSelectedPerson(selectedPerson);
        changeScenes1();
    }


    public void remove(){
        ObservableList<Person> personSelected, allPersons;
        allPersons = table.getItems();
        personSelected = table.getSelectionModel().getSelectedItems();
        if (personSelected.isEmpty()) return;
        personSelected.forEach(allPersons::remove);
        list.remove(personSelected);
        DBOperations.remove(connection,personSelected.get(0));
    }


}
