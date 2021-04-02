
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

public class ReservationController implements Initializable {

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

    @FXML
    private Label missingInfo;

    @FXML
    private Label wrongPhone;

    @FXML
    private Label wrongDate;

    @FXML
    private Label wrongNumber;

    @FXML
    private Label wrongTime;


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
        setVisibility();

    }

    public void save() {
        setVisibility();
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

        LocalDate timeNow = LocalDate.now();

        ArrayList<String> results = new ArrayList<>();
        results.addAll(Arrays.asList(name, surname, street, city, houseNumber, plate, time, type));
        for (String result : results) {
            if (result.isEmpty()) {
                missingInfo.setVisible(true);
                return null;
            }
        }

        if(timeNow.isAfter(localDate)){
            wrongDate.setVisible(true);
            return null;

        }

        if(time == null || time.isEmpty()){
            wrongTime.setVisible(true);
            return null;
        }

        if(Integer.parseInt(houseNumber) < 0 || houseNumber.equals(Integer.parseInt(houseNumber))){
            wrongNumber.setVisible(true);
            return null;
        }

        if(phone.isEmpty() || phone.length() != 13 || !phone.startsWith("+420")){
            wrongPhone.setVisible(true);
            return null;
        }

        if (email.isEmpty() && phone.isEmpty()) {
            saveButton.setText("Email nebo telefonni cislo neni vyplneno.");
            return null;
        }

        Address address = new Address(city,street,houseNumber);
        Person person = new Person(name,surname,phone,email,address);
        Vehicle vehicle = new Vehicle(plate, type);

        time = time.substring(0,time.indexOf('-')) + ":00";
        LocalTime newTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
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

    public void changeScene() throws Exception {
        SceneManager.changeScene("table.fxml");
    }

    private void setVisibility(){
        missingInfo.setVisible(false);
        wrongPhone.setVisible(false);
        wrongDate.setVisible(false);
        wrongNumber.setVisible(false);
        wrongTime.setVisible(false);
    }

}