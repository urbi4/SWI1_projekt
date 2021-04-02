package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneManager {
    private static Scene mainScene;
    private static Scene records;
    private static Stage stage;

    public static void setMainScene(Stage primarystage) throws IOException {
        stage = primarystage;
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("table.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        stage.setTitle("Registrace objednávky");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
//        loader = new FXMLLoader(SceneManager.class.getResource("records.fxml"));
//        root = loader.load();
//        records = new Scene(root, 600, 600);
    }

    public static void setRecordsScene(){
        stage.setScene(records);
        stage.show();
    }

    public static void changeScene(String fxml) throws Exception{
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root,600,650));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
