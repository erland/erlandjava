package erland.game.component.swing;

import erland.game.component.ELabel;

import javax.swing.*;

/**
 * A Swing implementation for a ELabel
 * @author Erland Isaksson
 */
public class ESwingLabel extends ELabel {
    /**
     * Creates a new instance
     * @param label The text of the label
     */
    public ESwingLabel(String label) {
        super(new JLabel(label));
    }
}
