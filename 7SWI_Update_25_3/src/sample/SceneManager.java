package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneManager {
    private static Scene mainScene;
    private static Scene records;
    private static Stage stage;

    public static void setMainScene(Stage primarystage) throws IOException {
        stage = primarystage;
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        stage.setTitle("Registrace objednávky");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
        loader = new FXMLLoader(SceneManager.class.getResource("records.fxml"));
        root = loader.load();
        records = new Scene(root, 600, 600);
    }

    public static void setRecordsScene(){
        stage.setScene(records);
        stage.show();
    }

}
