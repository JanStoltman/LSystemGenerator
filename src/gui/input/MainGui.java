package gui.input;

import callback.DialogCallback;
import callback.GraphicsGenerateCallback;
import callback.LSytstemGenerateCallback;
import graphics.GraphicsGenerator;
import gui.output.logic.ResultDisplay;
import kotlin.Pair;
import lsystem.LSystemGenerator;
import model.LSystemGeneratorData;

import javax.swing.*;
import java.util.ArrayList;

public class MainGui implements DialogCallback, LSytstemGenerateCallback, GraphicsGenerateCallback {
    private JPanel mainPanel;
    private JTextField axiomInput;
    private JButton generujButton;
    private JTextField transformationsInput;
    private JTextField IterationsInput;
    private static JFrame mainFrame;
    private String lSystem = "";

    public MainGui() {
        generujButton.addActionListener(actionEvent -> {
            String axiom = axiomInput.getText().trim();
            int iterations = Integer.valueOf(IterationsInput.getText().trim());
            System.out.println("Axiom: " + axiom);
            System.out.println("Iteratins " + iterations);

            ArrayList<Pair<String, String>> transformations = getAxioms();

            LSystemGenerator.Companion.generateLSystem(new LSystemGeneratorData(axiom, iterations, transformations), this);
        });
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

    public static void main(String[] args) {
        mainFrame = new JFrame("MainGui");
        mainFrame.setContentPane(new MainGui().mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    @Override
    public void onOk() {
        new GraphicsGenerator().generate3DLSystem(lSystem, this);
    }

    @Override
    public void onCancel() {
        mainFrame.setVisible(true);
    }

    @Override
    public void onGenerated(String lSystem) {
        mainFrame.setVisible(false);
        this.lSystem = lSystem;
        JDialog dialog = new LSystemResultDialog(lSystem, this);

        dialog.setSize(500, 500);
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void onModelGenerated() {
        new ResultDisplay().displayOutputView();
    }
}
