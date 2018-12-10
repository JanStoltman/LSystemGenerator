package gui.input;

import callback.DialogCallback;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LSystemResultDialog extends Application {
    private final String lSystem;
    private final DialogCallback callback;
    private Stage primaryStage;

    public LSystemResultDialog(String lSystem, DialogCallback mainGui) {
        this.lSystem = lSystem;
        this.callback = mainGui;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("dialog.fxml"));
        Scene mainScene = new Scene(root, 800, 400);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("L-System 3D Generator");
        primaryStage.show();


        ((TextArea) mainScene.lookup("#resultT")).setWrapText(true);
        ((TextArea) mainScene.lookup("#resultT")).setText(lSystem);

        ((Button) mainScene.lookup("#generate")).setOnAction(e -> {
            primaryStage.close();
            callback.onOk();
        });
        ((Button) mainScene.lookup("#cacel")).setOnAction(e -> {
            primaryStage.close();
            callback.onCancel();
        });
    }
}
