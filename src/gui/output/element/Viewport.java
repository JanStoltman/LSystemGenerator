package gui.output.element;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import gui.output.logic.ResultDisplay;
import gui.output.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class Viewport extends GLJPanel {
    public static String PROFILE = GLProfile.GL2;

    private static final long serialVersionUID = 1L;

    private Renderer renderer;
    private Point startLMB;
    private Point startRMB;

    public Viewport(Renderer renderer, String name) {
        super(ResultDisplay.CAPS);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(new JLabel(name));
        this.setRenderer(renderer);
        this.addGLEventListener(renderer);
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}

