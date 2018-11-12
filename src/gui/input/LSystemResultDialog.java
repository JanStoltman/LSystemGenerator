package gui.input;

import callback.DialogCallback;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LSystemResultDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea resultArea;

    public LSystemResultDialog(String lSystem, DialogCallback callback) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        resultArea.setText(lSystem);

        buttonOK.addActionListener(e -> {
            dispose();
            callback.onOk();
        });
        buttonCancel.addActionListener(e -> {
            dispose();
            callback.onCancel();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                callback.onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> {
            this.dispose();
            callback.onCancel();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
