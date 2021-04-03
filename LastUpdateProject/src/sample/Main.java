package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.Connector;
import sample.database.DBSOperations;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneManager.setMainScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
