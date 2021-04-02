package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalTimeStringConverter;
import sample.database.*;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField name;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField surname;

    @FXML
    private Label emaillabel;

    @FXML
    private TextField email;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phone;

    @FXML
    private Label streetLabel;

    @FXML
    private Label houseNumberLabel;

    @FXML
    private TextField houseNumber;

    @FXML
    private Label cityLabel;

    @FXML
    private TextField street;

    @FXML
    private TextField city;

    @FXML
    private DatePicker date;

    @FXML
    private ListView<String> list;

    @FXML
    private ChoiceBox<String> chooseBox;

    @FXML
    private Label plateLabel;

    @FXML
    private TextField plate;

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox pneuCB;

    @FXML
    private CheckBox oilCB;

    @FXML
    private CheckBox batCB;

    @FXML
    private CheckBox acCB;

    @FXML
    private CheckBox wipCB;

    @FXML
    private CheckBox comCB;

    @FXML
    private CheckBox geoCB;

    @FXML
    private GridPane grid;

    @FXML
    private Button changeScene;

    private ObservableList<String> toAdd;
    private ObservableList<String> choose;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();

        toAdd = FXCollections.observableArrayList(DBSOperations.getTimes(connection));
        list.setItems(toAdd);
        choose = FXCollections.observableArrayList(DBSOperations.getTypes(connection));
        chooseBox.getItems().addAll(choose);

    }

    public void save() {

        Order order = check();
        if (order == null){return;} // CHYBA
        DBSOperations.add(connection, order);


    }

    private Order check() {
        String name = this.name.getText();
        String surname = this.surname.getText();
        String street = this.street.getText();
        String city = this.city.getText();
        String houseNumber = this.houseNumber.getText();
        String plate = this.plate.getText();
        String time = this.list.getSelectionModel().getSelectedItem();
        String type = chooseBox.getValue();

        String email = this.email.getText();
        String phone = this.phone.getText();

        LocalDate localDate = date.getValue();
        String[] checkBoxes = getCheckboxes();

        ArrayList<String> results = new ArrayList<>();
        results.addAll(Arrays.asList(name, surname, street, city, houseNumber, plate, time, type));
        for (String result : results) {
            if (result.isEmpty()) {
                saveButton.setText("Pole není vyplněno.");
                return null;
            }
        }

        if (email.isEmpty() && phone.isEmpty()) {
            saveButton.setText("Email nebo telefonni cislo neni vyplneno.");
            return null;
        }

        Address address = new Address(city,street,houseNumber);
        Person person = new Person(name,surname,phone,email,address);
        Vehicle vehicle = new Vehicle(plate, type);

        time = "0" + time.substring(0,time.indexOf('-')) + ":00";
        LocalTime newTime = LocalTime.parse(time,DateTimeFormatter.ISO_LOCAL_TIME);
        return new Order(localDate,person,vehicle,checkBoxes,newTime);


    }

    private String[] getCheckboxes() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        String[] names = {"PNEU","OIL","BATTERY","AC","WIPER","COMPLETE","GEOMETRY"}; //LINK NA DB ?
        checkBoxes.addAll(Arrays.asList(pneuCB, oilCB, batCB, acCB, wipCB, comCB, geoCB));
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if(checkBoxes.get(i).isSelected()){
                result.add(names[i]);
            }
        }
        return result.toArray(new String[0]);
    }

    public void changeScene(){
        SceneManager.setRecordsScene();
    }

}
