package erland.game.component.awt;

import erland.game.component.ETextField;

import java.awt.*;

/**
 * An AWT implementation for ETextField
 * @author Erland Isaksson
 */
public class EAWTTextField extends ETextField {
    /**
     * Creates a new instance
     * @param label The text of the new text field
     */
    public EAWTTextField(String label) {
        super(new TextField(label));
    }

    public void setEditable(boolean editable) {
        ((TextField)getComponent()).setEditable(editable);
    }
}
