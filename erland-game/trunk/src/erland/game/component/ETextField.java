package erland.game.component;

import erland.game.component.awt.EAWTTextField;
import erland.game.component.swing.ESwingTextField;

import java.awt.*;

/**
 * Represents a TextField object
 * @author Erland Isaksson
 */
public abstract class ETextField extends EComponent {
    /**
     * Initiates a new instance
     * @param component The component of the new instance
     */
    protected ETextField(Component component) {
        super(component);
    }
    /**
     * Creates a new ETextField using the selected implementation
     * @param label The label of the text field
     * @return The newly created ETextField object
     */
    public static ETextField create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTTextField(label);
        }else {
            return new ESwingTextField(label);
        }
    }
    /**
     * Sets if the text field should be editable of not
     * @param editable true/false (Editable/Not editable)
     */
    public abstract void setEditable(boolean editable);
}
