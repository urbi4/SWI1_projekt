package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.awt.event.MouseAdapter;



import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.awt.event.ActionListener;

public class ReservationController extends MouseAdapter implements Initializable{


    ObservableList<String> checkBox = FXCollections.observableArrayList("9:00","9:30","10:00","10:30","11:00","11:30","12:00");

    @FXML
    private TextField inputName;
    @FXML
    private TextField inputSurname;
    @FXML
    private TextField inputAddress;
    @FXML
    private TextField inputPhone;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private DatePicker selectDate;
    @FXML
    private Button submitButton;
    @FXML
    private Label personAlreadyCreated;
    @FXML
    private Label successText;
    @FXML
    private Label missingInfo;
    @FXML
    private Label dateAlreadyBooked;
    @FXML
    private Label dateInPast;
    @FXML
    private Label phoneFormat;
    @FXML
    private Label todayDate;
    @FXML
    private Button closeButton;

    private Connection connection;

    private ObservableList<Person> list = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
        ArrayList<Person> ret = DBOperations.getList(connection);
        for(Person person : ret){
            list.add(person);
        }
        choiceBox.setItems(checkBox);
        setVisibility();


    }

    public ObservableList<String> getCheckBox() {
        return checkBox;
    }

    public void add() throws Exception {
        setVisibility();
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dtf1 =DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        timeNow.format(dtf);
        if(timeNow.getHour() < 10) timeNow.format(dtf1);
        String dateText = selectDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeText = choiceBox.getSelectionModel().getSelectedItem().toString();
        String ret = dateText+" "+timeText;
        LocalDateTime localDateTime = LocalDateTime.parse(ret,dtf);
        localDateTime.format(dtf);
        String nameText = inputName.getText();
        String surnameText = inputSurname.getText();
        String addressText = inputAddress.getText();
        String phoneText = inputPhone.getText();

        if (checkConditions(timeNow, dateText, timeText, localDateTime, nameText, surnameText, addressText, phoneText)) return;

        Person person = new Person(nameText,surnameText,addressText,phoneText,dateText,timeText);
        list.add(person);
        DBOperations.add(connection,person);
        successText.setVisible(true);
        checkBox.remove(timeText);
    }

    private boolean checkConditions(LocalDateTime timeNow, String dateText, String timeText, LocalDateTime localDateTime, String nameText, String surnameText, String addressText, String phoneText) {
        if (nameText.length() < 1 || surnameText.length() < 1 || addressText.length() < 1 || phoneText.length() < 1 || dateText.length() < 1 || timeText.length() < 1) {
            missingInfo.setVisible(true);
            missingInfo.getText();
            return true;
        }
        if(timeNow.isAfter(localDateTime) || (timeNow.equals(localDateTime) && timeNow.getMonth().equals(localDateTime.getMonth()))){
            if(timeNow.getHour() < Integer.parseInt(timeText.substring(0,2)) && (timeNow.equals(localDateTime) && timeNow.getMonth().equals(localDateTime.getMonth()))){
                todayDate.setVisible(true);
                return true;
            }
            else if(timeNow.isAfter(localDateTime)){
                dateInPast.setVisible(true);
                return true;
            }
            else return false;

        }

        if(phoneText.length() != 13 || !phoneText.startsWith("+420")){
            phoneFormat.setVisible(true);
            return true;
        }


        for (Person person : list) {
            if(person.getDate().equals(dateText) && person.getTime().equals(timeText)){
                dateAlreadyBooked.setVisible(true);
                checkBox.remove(timeText);
                return true;
            }
        }
        for (Person person : list)
            if (person.getName().equals(nameText) || person.getSurname().equals(surnameText) || person.getAddress().equals(inputAddress) || (person.getDate().equals(dateText) && person.getTime().equals(timeText))) {
                personAlreadyCreated.setVisible(true);
                return true;
            }
        return false;
    }


    public void closeButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    public void changeScenes() throws Exception {
        Main m = new Main();
        m.changeScene("sample.fxml");
    }

    private void setVisibility() {
        missingInfo.setVisible(false);
        successText.setVisible(false);
        personAlreadyCreated.setVisible(false);
        dateAlreadyBooked.setVisible(false);
        dateInPast.setVisible(false);
        phoneFormat.setVisible(false);
        todayDate.setVisible(false);
    }


}
