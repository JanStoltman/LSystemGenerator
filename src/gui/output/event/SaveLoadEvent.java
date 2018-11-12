package gui.output.event;

import java.awt.*;

public class SaveLoadEvent extends AWTEvent {
    public final static int SAVE = 0;
    public final static int LOAD = 1;

    public SaveLoadEvent(Object source, int id) {
        super(source, id);
    }
}
