package erland.game.component;

import erland.game.component.swing.ESwingButton;
import erland.game.component.awt.EAWTButton;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents a Button object
 * @author Erland Isaksson
 */
public abstract class EButton extends EComponent {
    /**
     * Initializes a new instance
     * @param component The Component object for this button
     */
    protected EButton(Component component) {
        super(component);
    }
    /**
     * Creates a new EButton using the selected implementation
     * @param label The text of the button
     * @return The newly created EButton object
     */
    public static EButton create(String label) {
        if(EComponentMode.isApplet()) {
            return new EAWTButton(label);
        }else {
            return new ESwingButton(label);
        }
    }
    /**
     * Add an ActionListener to this button
     * @param actionListener The action listener to add to this button
     */
    public abstract void addActionListener(ActionListener actionListener);

    /**
     * Removes an ActionListener from this button
     * @param actionListener The action listener to remove from this button
     */
    public abstract void removeActionListener(ActionListener actionListener);
}
