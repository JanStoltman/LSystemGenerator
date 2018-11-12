package gui.output.event;

import java.util.EventListener;

public interface CameraPositionEventListener extends EventListener {
    void onCameraPositionEvent(CameraPositionEvent e);
}
