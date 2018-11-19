package gui.output.renderer;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;

import static com.jogamp.opengl.GL.*;

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
    protected IntBuffer textureNames = GLBuffers.newDirectIntBuffer(2);
    protected String[] texturePaths = new String[]{"//home//yggdralisk//Desktop//objs//bark.png", "//home//yggdralisk//Desktop//objs//leaves.png"};

    public Renderer() {
        lighting = new Lighting();
    }

    public void setMeshList(List<Mesh> meshes) {
        this.meshes = meshes;
    }

    public void renderMeshes(GL2 gl) {
        gl.glGenTextures(2, textureNames);
        try {
            for (int i = 0; i < 2; i++) {
                TextureData textureData = TextureIO.newTextureData(GLProfile.get(GLProfile.GL2),
                        new File(texturePaths[i]),
                        false,
                        TextureIO.PNG);

                gl.glBindTexture(GL_TEXTURE_2D, textureNames.get(i));
                gl.glTexImage2D(GL_TEXTURE_2D,
                        0,
                        textureData.getInternalFormat(),
                        textureData.getWidth(),
                        textureData.getHeight(),
                        textureData.getBorder(),
                        textureData.getPixelFormat(),
                        textureData.getPixelType(),
                        textureData.getBuffer());

                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
                gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

                // Generate mip maps
                gl.glGenerateMipmap(GL_TEXTURE_2D);

                // Deactivate texture
                gl.glBindTexture(GL_TEXTURE_2D, 0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (meshes != null) {
            for (Mesh mesh : meshes) {
                gl.glActiveTexture(GL_TEXTURE0);

                for (int i = 0; i < mesh.getFaceCount(); i++) {
                    try {
                        String textureName = mesh.getTT(mesh.getFV(i, 0));

                        if(textureName.trim().equals("bark")){
                            gl.glBindTexture(gl.GL_TEXTURE_2D, textureNames.get(0));
                        }else{
                            gl.glBindTexture(gl.GL_TEXTURE_2D, textureNames.get(1));
                        }

                        gl.glBegin(GL.GL_TRIANGLES);
                        Vector color = mesh.getCC(mesh.getFV(i, 0));

                        float r = color.getX() / 255;
                        float g = color.getY() / 255;
                        float b = color.getZ() / 255;

                        gl.glColor3f(r, g, b);

                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mesh.getSC(mesh.getFS(i), Surface.AMBIENT_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mesh.getSC(mesh.getFS(i), Surface.DIFFUSE_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mesh.getSC(mesh.getFS(i), Surface.SPECULAR_COMPONENT), 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, mesh.getSC(mesh.getFS(i), Surface.SHININESS_COMPONENT), 0);

                        gl.glTexCoord2f(0f, 0f);
                        gl.glNormal3f(mesh.getNC(mesh.getFN(i, 0), 0), mesh.getNC(mesh.getFN(i, 0), 1), mesh.getNC(mesh.getFN(i, 0), 2));
                        gl.glVertex3f(mesh.getVC(mesh.getFV(i, 0)).getX(), mesh.getVC(mesh.getFV(i, 0)).getY(), mesh.getVC(mesh.getFV(i, 0)).getZ());

                        gl.glTexCoord2f(0f, 1f);
                        gl.glNormal3f(mesh.getNC(mesh.getFN(i, 1), 0), mesh.getNC(mesh.getFN(i, 1), 1), mesh.getNC(mesh.getFN(i, 1), 2));
                        gl.glVertex3f(mesh.getVC(mesh.getFV(i, 1)).getX(), mesh.getVC(mesh.getFV(i, 1)).getY(), mesh.getVC(mesh.getFV(i, 1)).getZ());

                        gl.glTexCoord2f(1f, 0f);
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
        gl.glDisable(GL_TEXTURE_2D);
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
