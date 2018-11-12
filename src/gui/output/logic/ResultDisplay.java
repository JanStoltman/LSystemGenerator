package gui.output.logic;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import gui.output.element.Toolbar;
import gui.output.element.Viewport;
import gui.output.event.SaveLoadEvent;
import gui.output.renderer.OrthoRenderer;
import gui.output.renderer.PerspectiveRenderer;
import model.Camera;
import model.Vector;

import javax.swing.*;
import java.awt.*;

public class ResultDisplay {
    public static GLCapabilities CAPS = new GLCapabilities(GLProfile.get(GLProfile.GL2));

    public static void main(String[] arg) {
        new ResultDisplay().displayOutputView();
    }

    public void displayOutputView() {
        CAPS.setDepthBits(64);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Wynik");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Controller controller = new Controller(frame);

        Camera camera = new Camera(100, 0.01f, 1000, new Vector(10f, 10f, 10f), new Vector(0, 0, 0));
        controller.setCamera(camera);

        OrthoRenderer topRenderer = new OrthoRenderer(OrthoRenderer.VIEW_TOP);
        OrthoRenderer frontRenderer = new OrthoRenderer(OrthoRenderer.VIEW_FRONT);
        OrthoRenderer leftRenderer = new OrthoRenderer(OrthoRenderer.VIEW_LEFT);
        PerspectiveRenderer perspectiveRenderer = new PerspectiveRenderer();

        Viewport top = new Viewport(topRenderer, "Góra");
        controller.addViewport(top);

        Viewport front = new Viewport(frontRenderer, "Przód");
        controller.addViewport(front);

        Viewport left = new Viewport(leftRenderer, "Lewo");
        controller.addViewport(left);

        Viewport perspective = new Viewport(perspectiveRenderer, "Perpektywa");
        controller.addViewport(perspective);

        Toolbar toolbar = new Toolbar();
        toolbar.setPreferredSize(new Dimension(100, 100));
        toolbar.setMinimumSize(toolbar.getPreferredSize());
        controller.setToolbar(toolbar);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.getContentPane().add(perspective, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.getContentPane().add(top, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.getContentPane().add(front, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.getContentPane().add(left, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.getContentPane().add(toolbar, gbc);

        frame.setSize(1000, 1000);
        frame.setVisible(true);

        controller.onSaveLoadEvent(new SaveLoadEvent(this, SaveLoadEvent.LOAD));
    }
}
