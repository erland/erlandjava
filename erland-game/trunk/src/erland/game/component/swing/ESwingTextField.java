package erland.game.component.swing;

import erland.game.component.ETextField;

import javax.swing.*;

/**
 * A Swing implementation for a ETextField
 * @author Erland Isaksson
 */
public class ESwingTextField extends ETextField {
    /**
     * Creates a new instance
     * @param label The text of the new text field object
     */
    public ESwingTextField(String label) {
        super(new JTextField(label));
    }

    public void setEditable(boolean editable) {
        ((JTextField)getComponent()).setEditable(editable);
    }
}
