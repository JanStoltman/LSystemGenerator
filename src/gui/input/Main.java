package gui.input;

import callback.DialogCallback;
import callback.GraphicsGenerateCallback;
import callback.LSytstemGenerateCallback;
import graphics.GraphicsGenerator;
import gui.output.logic.ResultDisplay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotlin.Pair;
import lsystem.LSystemGenerator;
import model.LSystemGeneratorData;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application implements DialogCallback, LSytstemGenerateCallback, GraphicsGenerateCallback {

    private TextField axiomInput;
    private Button generujButton;
    private TextField transformationsInput;
    private TextField iterationsInput;
    private TextField angle;
    private String lSystem = "";
    private Scene mainScene;

    private Stage primaryStage;
    private Stage dialogStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            mainScene = new Scene(root, 600, 400);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("L-System 3D Generator");
            primaryStage.show();

            axiomInput = (TextField) mainScene.lookup("#axiom_input");
            generujButton = (Button) mainScene.lookup("#confirm");
            transformationsInput = (TextField) mainScene.lookup("#transformations");
            angle = (TextField) mainScene.lookup("#angle");
            iterationsInput = (TextField) mainScene.lookup("#iterations");

            generujButton.setOnAction(actionEvent -> {
                String axiom = axiomInput.getText().trim();
                int iterations = Integer.valueOf(iterationsInput.getText().trim());
                System.out.println("Axiom: " + axiom);
                System.out.println("Iteratins " + iterations);

                ArrayList<Pair<String, String>> transformations = getAxioms();

                LSystemGenerator.Companion.generateLSystem(new LSystemGeneratorData(axiom, iterations, transformations, Float.parseFloat(angle.getText().trim())), this);
                primaryStage.hide();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOk() {
        new GraphicsGenerator().generate3DLSystem(lSystem, Float.parseFloat(angle.getText().trim()), this);
    }

    @Override
    public void onCancel() {
        primaryStage.show();
    }

    @Override
    public void onGenerated(String lSystem) {
        this.lSystem = lSystem;
        LSystemResultDialog dialog = new LSystemResultDialog(lSystem, this);
        try {
            dialog.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onModelGenerated(long vertexCount, long facesCount) {
        System.out.println("Vertex generated: " + vertexCount);
        System.out.println("Faces generated: " + facesCount);

        new ResultDisplay().displayOutputView();
    }

    private ArrayList<Pair<String, String>> getAxioms() {
        String[] raw = transformationsInput.getText().trim()
                .split(" ");
        ArrayList<Pair<String, String>> temp = new ArrayList<>();

        System.out.println("Transformations:");

        for (String s : raw) {
            String[] spl = s.split("->");
            System.out.println("\t" + spl[0] + "  ->  " + spl[1]);
            temp.add(new Pair(spl[0], spl[1]));
        }

        return temp;
    }
}
