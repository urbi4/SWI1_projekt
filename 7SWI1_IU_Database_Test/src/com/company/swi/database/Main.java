package com.company.swi.database;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

public class Main extends Application{

    private Stage window;
    private Scene scene;
    private static Connection connection;
    private VBox vBox;
    TextField textField;
    private HashSet<String> list = new HashSet<>();

    public static void main(String[] args) {
        connection = Connector.connect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        HBox hBox = new HBox(10);
        vBox = new VBox(10);

        String cssLayout = "-fx-border-color: gray;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-padding: 10;\n";
        vBox.setStyle(cssLayout);
        vBox.setPrefWidth(100);

        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().add(vBox);

        Button button = new Button("Print database");
        button.setOnAction(e -> printDatabase());
        hBox.getChildren().add(button);

        textField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveToDatabase());
        hBox.getChildren().addAll(textField, saveButton);

        scene = new Scene(hBox, 500, 200);

        window.setScene(scene);
        window.setOnCloseRequest(e -> window.close());
        window.setTitle("REGISTRACE");
        window.show();

    }

    private void saveToDatabase() {
        DBSOperations.add(connection, textField.getText(), 0);
    }

    private void printDatabase() {
        ArrayList<String> newList = DBSOperations.getList(connection);
        for (String s : newList) {
            if(list.isEmpty() || !list.contains(s)){
                Label label = new Label(s);
                list.add(s);
                vBox.getChildren().add(label);
            }
        }
    }


}
