
package sample;

import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.layout.GridPane;
        import sample.database.*;

        import java.net.URL;
        import java.sql.Connection;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.time.format.DateTimeFormatter;
        import java.util.*;

/**
 * Controls reservation/edit stage
 */
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

    @FXML
    private Label successOrder;

    @FXML
    private Label wrongChoice;

    @FXML
    private Label wrongEmail;

    private Order orderToLoad;
    private ObservableList<String> toAdd;
    private ObservableList<String> choose;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.connection = Connector.connect();
        this.orderToLoad = TableController.getSelectedOrder();
        this.toAdd = FXCollections.observableArrayList();
        this.list.setItems(toAdd);
        this.choose = FXCollections.observableArrayList(DBSOperations.getTypes(connection));
        this.chooseBox.getItems().addAll(choose);
        this.date.valueProperty().addListener(observable -> {
            toAdd = FXCollections.observableArrayList(DBSOperations.getAvailableTimes(connection,date.getValue()));
            list.setItems(toAdd);
        });
        manageOrderToLoad();
        setVisibility();
    }

    private void manageOrderToLoad() {
        if(orderToLoad == null) return;
        setOrderToLoad(orderToLoad);
    }

    /**
     * According to the result of check, creates or disposes new order
     */
    public void saveOrder() {
        setVisibility();
        Order order = checkInputFromUser();
        if (order == null){return;}
        if(orderToLoad != null) DBSOperations.remove(connection,orderToLoad);
        DBSOperations.add(connection, order);
    }

    /**
     * Sets order that was selected by user
     * @param orderToLoad
     */
    private void setOrderToLoad(Order orderToLoad) {
        name.setText(orderToLoad.getPerson().getName());
        surname.setText(orderToLoad.getPerson().getSurname());
        city.setText(orderToLoad.getPerson().getAddress().getCity());
        street.setText(orderToLoad.getPerson().getAddress().getStreet());
        houseNumber.setText(orderToLoad.getPerson().getAddress().getHouseNumber());
        plate.setText(orderToLoad.getVehicle().getPlateNumber());
        email.setText(orderToLoad.getPerson().getEmail());
        phone.setText(orderToLoad.getPerson().getPhoneNumber());
        chooseBox.setValue(orderToLoad.getVehicle().getVehicleType());
        date.setValue(orderToLoad.getDate());
        checkCheckboxes(orderToLoad);
    }

    /**
     * Sets states of checkboxes according to the selected order
     * @param orderToLoad selected order
     */
    private void checkCheckboxes(Order orderToLoad) {
        Map<String, CheckBox> checkBoxes = new TreeMap<>(){{
            put("PNEU",pneuCB);
            put("OIL",oilCB);
            put("BATTERY",batCB);
            put("AC",acCB);
            put("WIPER",wipCB);
            put("COMPLETE",comCB);
            put("GEOMETRY",geoCB);
        }};
        for (String problem : orderToLoad.getProblems()) {
            if (checkBoxes.containsKey(problem)){
                checkBoxes.get(problem).setSelected(true);
            }
        }
    }

    /**
     * Checks inserted values
     * @return Returns validated order if it's successful
     */
    private Order checkInputFromUser() {
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
        ArrayList<String> checkBoxes = getCheckboxes();

        LocalDate timeNow = LocalDate.now();

        ArrayList<String> results = new ArrayList<>();
        results.addAll(Arrays.asList(name, surname, street, city, houseNumber, plate, time, type));
        for (String result : results) {
            if (result == null || result.isEmpty()) {
                missingInfo.setVisible(true);
                return null;
            }
        }

        if (!email.contains(".cz") && (!email.contains("@")) || (!email.contains(".com") && !email.contains("@"))){
            wrongEmail.setVisible(true);
            return null;
        }

        if(checkBoxes.isEmpty()) {
            wrongChoice.setVisible(true);
            return null;
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
        if((phone.length() != 13) && (!phone.startsWith("+421") || !phone.startsWith("+420"))){
            wrongPhone.setVisible(true);
            return null;
        }

        if (email.isEmpty() && phone.isEmpty()) {
            saveButton.setText("Email nebo telefonni cislo neni vyplneno.");
            return null;
        }

        Address address = new Address(city,street,houseNumber);
        phone = phone.substring(4);
        Person person = new Person(name,surname,phone,email,address);
        Vehicle vehicle = new Vehicle(plate, type);

        time = time.substring(0,time.indexOf('-')) + ":00";
        LocalTime newTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
        successOrder.setVisible(true);
        return new Order(null,localDate,person,vehicle,checkBoxes,newTime,null);
    }

    /**
     * Gets info about what checkboxes are checked - problems
     * @return Returns selected problems
     */
    private ArrayList<String> getCheckboxes() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        String[] names = {"PNEU","OIL","BATTERY","AC","WIPER","COMPLETE","GEOMETRY"}; //LINK NA DB ?
        checkBoxes.addAll(Arrays.asList(pneuCB, oilCB, batCB, acCB, wipCB, comCB, geoCB));
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if(checkBoxes.get(i).isSelected()){
                result.add(names[i]);
            }
        }
        return result;
    }

    /**
     * Changes scene to primary window
     */
    public void changeSceneToTable() throws Exception {
        SceneManager.changeScene("table.fxml");
    }

    /**
     * Hides warning labels
     */
    private void setVisibility(){
        missingInfo.setVisible(false);
        wrongPhone.setVisible(false);
        wrongDate.setVisible(false);
        wrongNumber.setVisible(false);
        wrongTime.setVisible(false);
        successOrder.setVisible(false);
        wrongChoice.setVisible(false);
        wrongEmail.setVisible(false);
    }

}