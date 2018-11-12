package gui.output.event;

public interface ToolbarEventListener extends CameraPositionEventListener {
    void onCameraAngleEvent(CameraAngleEvent e);
    void onSaveLoadEvent(SaveLoadEvent e);
}
