package erland.game.component.awt;

import erland.game.component.EButton;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * An AWT implementation for a EButton
 * @author Erland Isaksson
 */
public class EAWTButton extends EButton {
    /**
     * Creates a new instance
     * @param label The text of the button
     */
    public EAWTButton(String label) {
        super(new Button(label));
    }

    public void addActionListener(ActionListener actionListener) {
        ((Button)getComponent()).addActionListener(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        ((Button)getComponent()).removeActionListener(actionListener);
    }
}
