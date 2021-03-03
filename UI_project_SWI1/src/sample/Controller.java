package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField input;
    @FXML
    private Button addButton;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, String> column;

    private Connection connection;

    private ObservableList<Person> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = Connector.connect();
        ArrayList<String> temp = DBOperations.getList(connection);
        for (String s : temp) {
            list.add(new Person(s));
        }
   //     column = new TableColumn<Person, String>("name");
        column.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
   //     column.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
    //    table = new TableView<Person>();
        table.setItems(list);
    }

    public void add() {
        String name = input.getText();
        if (name == null) {
            return;
        }
        input.setText("");
        for (Person person : list) {
            if (person.getName().equals(name)) {
                return;
            }
        }
        list.add(new Person(name));
        DBOperations.add(connection, name);
    }
}
