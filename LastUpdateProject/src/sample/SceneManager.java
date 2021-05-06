package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static Stage stage;

    public static void setMainScene(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("table.fxml"));
        Parent root = loader.load();
        stage.setTitle("Registrace objednávky");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }

    public static void changeScene(String fxml){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
            stage.setScene(new Scene(root,600,650));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
