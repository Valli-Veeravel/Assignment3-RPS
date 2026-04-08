package rps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rps.controller.RpsGameController;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainFx.class.getResource("/RpsGameView.fxml"));
        Parent root = loader.load();
        RpsGameController controller = loader.getController();

        stage.setOnCloseRequest(e -> controller.onWindowClose());
        stage.setTitle("RPS GUI");
        stage.setScene(new Scene(root, 760, 720));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
