package gui.output.renderer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import model.*;

import java.util.List;

public abstract class Renderer implements GLEventListener {
    public static final byte VIEW_TOP = 0;
    public static final byte VIEW_LEFT = 1;
    public static final byte VIEW_FRONT = 2;
    public static final int VIEW_PERSPECTIVE = 4;

    protected List<Mesh> meshes;
    protected GLU glu = new GLU();
    protected Camera camera;
    protected MeshBox worldBox;
    protected Lighting lighting;
    protected boolean shouldFullUpdate = false;
    protected byte viewType;

    public Renderer() {
        lighting = new Lighting();
    }

    public void setMeshList(List<Mesh> meshes) {
        this.meshes = meshes;
    }

    public void renderMeshes(GL2 gl) {
        if (meshes != null) {
            for (Mesh mesh : meshes) {
                for (int i = 0; i < mesh.getFaceCount(); i++) {
                    try {
                        gl.glBegin(GL.GL_TRIANGLES);
                        Vector color =  mesh.getCC(mesh.getFV(i, 0));
                        float r = color.getX() / 255;
                        float g = color.getY() / 255;
                        float b = color.getZ() / 255;

                        gl.glColor3f(r, g, b);

                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mesh.getSC(mesh.getFS(i), Surface.AMBIENT_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mesh.getSC(mesh.getFS(i), Surface.DIFFUSE_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mesh.getSC(mesh.getFS(i), Surface.SPECULAR_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mesh.getSC(mesh.getFS(i), Surface.SHININESS_COMPONENT), 0);

                        gl.glNormal3f(mesh.getNC(mesh.getFN(i, 0), 0), mesh.getNC(mesh.getFN(i, 0), 1), mesh.getNC(mesh.getFN(i, 0), 2));
                        gl.glVertex3f(mesh.getVC(mesh.getFV(i, 0)).getX(), mesh.getVC(mesh.getFV(i, 0)).getY(), mesh.getVC(mesh.getFV(i, 0)).getZ());

                        gl.glNormal3f(mesh.getNC(mesh.getFN(i, 1), 0), mesh.getNC(mesh.getFN(i, 1), 1), mesh.getNC(mesh.getFN(i, 1), 2));
                        gl.glVertex3f(mesh.getVC(mesh.getFV(i, 1)).getX(), mesh.getVC(mesh.getFV(i, 1)).getY(), mesh.getVC(mesh.getFV(i, 1)).getZ());

                        gl.glNormal3f(mesh.getNC(mesh.getFN(i, 2), 0), mesh.getNC(mesh.getFN(i, 2), 1), mesh.getNC(mesh.getFN(i, 2), 2));
                        gl.glVertex3f(mesh.getVC(mesh.getFV(i, 2)).getX(), mesh.getVC(mesh.getFV(i, 2)).getY(), mesh.getVC(mesh.getFV(i, 2)).getZ());
                        gl.glEnd();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        gl.glEnd();
                    }
                }
            }
            gl.glFlush();
        }
        gl.glColor3f(1, 1, 1);
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.5f, 0.5f, 0.5f, 5.0f);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    public void renderAxisGizmo(GL2 gl) {
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(1, 0, 0);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 0, 1);
        gl.glEnd();

        gl.glFlush();

        gl.glEnable(GL2.GL_LIGHTING);
    }

    public void setWorldBox(MeshBox worldBox) {
        this.worldBox = worldBox;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    public void setForceFullUpdate(boolean shouldFullUpdate) {
        this.shouldFullUpdate = shouldFullUpdate;
    }

    public void setUpDepthTest(GL2 gl) {
        gl.glDepthFunc(GL.GL_LESS);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    public void setUpLighting(GL2 gl) {
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lighting.getAmbient(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lighting.getDiffuse(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lighting.getPosition(), 0);
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, lighting.getLmAmbient(), 0);
        float[] f = {0.0f};
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, f, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
    }

    public int getViewType() {
        return viewType;
    }

}
