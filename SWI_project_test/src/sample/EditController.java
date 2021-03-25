package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.Connection;


public class EditController implements Initializable {


    @FXML
    private TextField inputName;
    @FXML
    private TextField inputSurname;
    @FXML
    private TextField inputAddress;
    @FXML
    private TextField inputPhone;
    @FXML
    private DatePicker editDate;
    @FXML
    private TextField selectedTime;
    @FXML
    private Button closeButton;

    private Connection connection;

    private ObservableList<Person> list = FXCollections.observableArrayList();

    ObservableList<String> checkBox = FXCollections.observableArrayList("9:00","9:30","10:00","10:30","11:00","11:30","12:00");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
        ArrayList<Person> ret = DBOperations.getList(connection);
        for (Person person : ret) {
            list.add(person);
        }
//        for (int i = 0; i < list.size(); i++) {
//            inputName.setText(list.get(i).getName());
//            inputSurname.setText(list.get(i).getSurname());
//            inputAddress.setText(list.get(i).getAddress());
//            inputPhone.setText(list.get(i).getPhone());
//        }
        ObservableList<Person> selectedPerson = Controller.getSelectedPerson();
        inputName.setText(selectedPerson.get(0).getName());
        inputSurname.setText(selectedPerson.get(0).getSurname());
        inputAddress.setText(selectedPerson.get(0).getAddress());
        inputPhone.setText(selectedPerson.get(0).getPhone());
        selectedTime.setText(selectedPerson.get(0).getTime());
        editDate.getEditor().setText(selectedPerson.get(0).getDate());
//        choiceBox.setItems(ReservationController);
    }


    public void save() {
        ObservableList<Person> selectedPerson = Controller.getSelectedPerson();
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dtf =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        timeNow.format(dtf);
        String dateText = editDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeText = selectedTime.getText();
        String ret = dateText+" "+timeText;
        LocalDateTime localDateTime = LocalDateTime.parse(ret,dtf);
        localDateTime.format(dtf);
        String nameText = inputName.getText();
        String surnameText = inputSurname.getText();
        String addressText = inputAddress.getText();
        String phoneText = inputPhone.getText();

        if(inputName.equals(selectedPerson.get(0).getName()) && inputSurname.equals(selectedPerson.get(0).getSurname()) && inputAddress.equals(selectedPerson.get(0).getAddress()) && inputPhone.equals(selectedPerson.get(0).getPhone()) && selectedTime.equals(selectedPerson.get(0).getTime()) && editDate.equals(selectedPerson.get(0).getDate()))
            return;
        else{
            Person updatedPerson = new Person(nameText,surnameText,addressText,phoneText,dateText,timeText);
            DBOperations.update(connection,updatedPerson);
        }

    }

    public void closeButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
