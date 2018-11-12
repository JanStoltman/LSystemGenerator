package gui.output.event;

import java.awt.*;

public class CameraAngleEvent extends AWTEvent {

    private float angle;

    public CameraAngleEvent(Object sourse, float angle) {
        super(sourse, 0);
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
