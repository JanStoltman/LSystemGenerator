package gui.output.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import model.Vector;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;

public class PerspectiveRenderer extends Renderer {


    public PerspectiveRenderer() {
        viewType = 4;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(gl.GL_COLOR_MATERIAL);
        gl.glEnable(GL_TEXTURE_2D);

        reshape(drawable, 0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());

        Vector eye = camera.getEye();
        Vector at = camera.getAt();
        Vector up = camera.getUp();

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(eye.getX(), eye.getY(), eye.getZ(), at.getX(), at.getY(), at.getZ(), up.getX(), up.getY(), up.getZ());

        renderAxisGizmo(gl);

        renderMeshes(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.5f, 0.5f, 0.5f, 5.0f);
        setUpDepthTest(gl);
        setUpLighting(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(camera.getAngle(), (float) width / (float) height, camera.getNear(), camera.getFar());
        camera.setAspectRatio((float) width / (float) height);
    }
}
