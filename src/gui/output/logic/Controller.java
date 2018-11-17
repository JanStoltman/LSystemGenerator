package gui.output.logic;

import gui.output.element.Toolbar;
import gui.output.element.Viewport;
import gui.output.event.CameraAngleEvent;
import gui.output.event.CameraPositionEvent;
import gui.output.event.SaveLoadEvent;
import gui.output.event.ToolbarEventListener;
import gui.output.renderer.OrthoRenderer;
import gui.output.renderer.Renderer;
import loader.ObjManager;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller implements ToolbarEventListener, MouseListener, MouseMotionListener {
    private static final ArrayList<Vertex> verts = new ArrayList<Vertex>(Arrays.asList(new Vertex(new Vector(1, 1, 1), new Vector(0, 0, 0)),
            new Vertex(new Vector(-1, -1, -1), new Vector(0, 0, 0))));
    public static final MeshBox DEFAULT_WORLD_BOX = new MeshBox(verts);
    private MeshBox worldBox = DEFAULT_WORLD_BOX;
    private List<Mesh> meshes;
    private List<Viewport> viewports;
    private Toolbar toolbar;
    private Camera camera;
    private Lighting lighting;
    private ObjManager objManager;
    private Point prevLMB;
    private Point prevRMB;

    public Controller(JFrame frame) {
        meshes = new ArrayList<>();
        viewports = new ArrayList<>();
        objManager = new ObjManager(frame);
        lighting = new Lighting();
    }

    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
        viewport.getRenderer().setMeshList(meshes);
        viewport.getRenderer().setWorldBox(worldBox);
        viewport.getRenderer().setCamera(camera);
        viewport.getRenderer().setLighting(lighting);
        if (viewport.getRenderer().getViewType() != Renderer.VIEW_PERSPECTIVE) {
            viewport.addMouseListener(this);
            viewport.addMouseMotionListener(this);
        }
    }

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public void clearMeshes() {
        meshes.clear();
        worldBox = DEFAULT_WORLD_BOX;
        updateViewports();
    }

    private void updateViewports() {
        for (Viewport viewport : viewports) {
            viewport.getRenderer().setForceFullUpdate(true);
            viewport.getRenderer().setLighting(lighting);
            viewport.getRenderer().setCamera(camera);
            viewport.repaint();
        }
    }

    private void updateWorldBox() {
        if (meshes != null && meshes.size() > 0) {
            worldBox.maxX = (-Float.MAX_VALUE);
            worldBox.maxY = (-Float.MAX_VALUE);
            worldBox.maxZ = (-Float.MAX_VALUE);

            worldBox.minX = (Float.MAX_VALUE);
            worldBox.minY = (Float.MAX_VALUE);
            worldBox.minZ = (Float.MAX_VALUE);

            for (Mesh mesh : meshes) {
                if (worldBox.maxX < mesh.box.maxX) {
                    worldBox.maxX = mesh.box.maxX;
                }
                if (worldBox.maxY < mesh.box.maxY) {
                    worldBox.maxY = mesh.box.maxY;
                }
                if (worldBox.maxZ < mesh.box.maxZ) {
                    worldBox.maxZ = mesh.box.maxZ;
                }

                if (worldBox.minX < mesh.box.minX) {
                    worldBox.minX = mesh.box.minX;
                }
                if (worldBox.minY < mesh.box.minY) {
                    worldBox.minY = mesh.box.minY;
                }
                if (worldBox.minZ < mesh.box.minZ) {
                    worldBox.minZ = mesh.box.minZ;
                }
            }
            worldBox.maxX = (1 * Math.max(camera.getEye().getX(), worldBox.maxX));
            worldBox.maxY = (1 * Math.max(camera.getEye().getY(), worldBox.maxY));
            worldBox.maxZ = (1 * Math.max(camera.getEye().getZ(), worldBox.maxZ));

            worldBox.minX = (1 * Math.min(camera.getEye().getX(), worldBox.minX));
            worldBox.minY = (1 * Math.min(camera.getEye().getY(), worldBox.minY));
            worldBox.minZ = (1 * Math.min(camera.getEye().getZ(), worldBox.minZ));

            worldBox.maxX = (1 * Math.max(camera.getAt().getX(), worldBox.maxX));
            worldBox.maxY = (1 * Math.max(camera.getAt().getY(), worldBox.maxY));
            worldBox.maxZ = (1 * Math.max(camera.getAt().getZ(), worldBox.maxZ));

            worldBox.minX = (Math.min(camera.getAt().getX(), worldBox.minX));
            worldBox.minY = (Math.min(camera.getAt().getY(), worldBox.minY));
            worldBox.minZ = (Math.min(camera.getAt().getZ(), worldBox.minZ));
        } else {
            worldBox = DEFAULT_WORLD_BOX;
        }
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        if (toolbar != null) {
            toolbar.setEye(camera.getEye());
            toolbar.setAt(camera.getAt());
        }
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    @Override
    public void onCameraPositionEvent(CameraPositionEvent e) {
        camera.setEye(e.getEye());
        camera.setAt(e.getAt());
        updateWorldBox();
        updateViewports();
        if (e.getSource() instanceof Viewport) {
            toolbar.setEye(e.getEye());
            toolbar.setAt(e.getAt());
        }
    }

    @Override
    public void onCameraAngleEvent(CameraAngleEvent e) {
        camera.setAngle(e.getAngle());
        updateViewports();
        updateWorldBox();
    }

    @Override
    public void onSaveLoadEvent(SaveLoadEvent e) {
        if (e.getID() == SaveLoadEvent.LOAD) {
            File f = new File("//home//yggdralisk//Desktop//objs//lsys//lsys.obj");
            if (objManager.loadObj(f)) {
                setCamera(objManager.getCamera());
                setLighting(objManager.getLighting());
                clearMeshes();
                addMesh(objManager.getMesh());
                updateWorldBox();
                updateViewports();
            }
        } else if (e.getID() == SaveLoadEvent.SAVE) {
            objManager.saveObj(meshes.get(0), lighting, camera);
        }
    }

    public void renderMeshes(List<Mesh> meshes) {
        clearMeshes();
        this.meshes = meshes;
        setCamera(objManager.getCamera());
        setLighting(objManager.getLighting());
        addMesh(objManager.getMesh());
        updateWorldBox();
        updateViewports();
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setToolbarEventListener(this);
        if (camera != null) {
            toolbar.setEye(camera.getEye());
            toolbar.setAt(camera.getAt());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        Viewport viewport = (Viewport) e.getSource();
        if (viewport.getRenderer() instanceof OrthoRenderer) {
            if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
                if (prevLMB != null) {
                    float dx = (float) (0.1 * (e.getPoint().getX() - prevLMB.getX()));
                    float dy = (float) (0.1 * (e.getPoint().getY() - prevLMB.getY()));
                    switch (viewport.getRenderer().getViewType()) {
                        case Renderer.VIEW_FRONT:
                            camera.getEye().setX(camera.getEye().getX() + dx);
                            camera.getEye().setY(camera.getEye().getY() - dy);
                            break;
                        case Renderer.VIEW_TOP:
                            camera.getEye().setX(camera.getEye().getX() + dx);
                            camera.getEye().setZ(camera.getEye().getZ() + dy);
                            break;
                        case Renderer.VIEW_LEFT:
                            camera.getEye().setZ(camera.getEye().getZ() + dx);
                            camera.getEye().setY(camera.getEye().getY() - dy);
                            break;
                    }
                    toolbar.setEye(camera.getEye());
                }
                prevLMB = e.getPoint();
            }

            if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
                if (prevRMB != null) {
                    float dx = (float) (0.1 * (e.getPoint().getX() - prevRMB.getX()));
                    float dy = (float) (0.1 * (e.getPoint().getY() - prevRMB.getY()));
                    switch (viewport.getRenderer().getViewType()) {
                        case Renderer.VIEW_FRONT:
                            camera.getAt().setX(camera.getAt().getX() + dx);
                            camera.getAt().setY(camera.getAt().getY() - dy);
                            break;
                        case Renderer.VIEW_TOP:
                            camera.getAt().setX(camera.getAt().getX() + dx);
                            camera.getAt().setZ(camera.getAt().getZ() + dy);
                            break;
                        case Renderer.VIEW_LEFT:
                            camera.getAt().setZ(camera.getAt().getZ() + dx);
                            camera.getAt().setY(camera.getAt().getY() - dy);
                            break;
                    }
                    toolbar.setAt(camera.getAt());
                }
                prevRMB = e.getPoint();
            }
        }

        updateViewports();
        updateWorldBox();

    }

    @Override
    public void mouseMoved(MouseEvent arg0) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Viewport viewport = (Viewport) e.getSource();
        if (viewport.getRenderer() instanceof OrthoRenderer) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                prevLMB = null;
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                prevRMB = null;
            }
        }
    }
}

