package erland.game.component.swing;

import erland.game.component.EButton;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * A Swing implementation for a EButton
 * @author Erland Isaksson
 */
public class ESwingButton extends EButton {
    /**
     * Creates a new instance
     * @param label The text of the new button
     */
    public ESwingButton(String label) {
        super(new JButton(label));
    }

    public void addActionListener(ActionListener actionListener) {
        ((AbstractButton)getComponent()).addActionListener(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        ((AbstractButton)getComponent()).removeActionListener(actionListener);
    }
}
