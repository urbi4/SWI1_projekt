package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        SplitPane root = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        AnchorPane root = FXMLLoader.load(Main.class.getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeScene(String fxml) throws Exception{
      try {
          Parent root = FXMLLoader.load(Main.class.getResource(fxml));
          Stage stage = new Stage();
          stage.initStyle(StageStyle.UNDECORATED);
          stage.setScene(new Scene(root,600,475));
          stage.show();
      }catch (Exception e){
          e.printStackTrace();
          e.getCause();
      }
    }
}
