package erland.game.component;

import erland.game.component.awt.EAWTLabel;
import erland.game.component.swing.ESwingLabel;

import java.awt.*;
/**
 * Represents a Label object
 * @author Erland Isaksson
 */
public class ELabel extends EComponent {
    /**
     * Initiates a new instance
     * @param component The component of the new instance
     */
    protected ELabel(Component component) {
        super(component);
    }
    /**
     * Creates a new ELabel using the selected implementation
     * @param label The text of the label object
     * @return The newly created ELabel object
     */
    public static ELabel create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTLabel(label);
        }else {
            return new ESwingLabel(label);
        }
    }
}
