package erland.game.component.awt;

import erland.game.component.ELabel;

import java.awt.*;
/**
 * An AWT implementation of the ELabel
 * @author Erland Isaksson
 */
public class EAWTLabel extends ELabel {
    /**
     * Creates a new instance
     * @param label The text of the new label
     */
    public EAWTLabel(String label) {
        super(new Label(label));
    }
}
