package erland.game.component.swing;

import erland.game.component.EPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing implementation for a EPanel
 * @author Erland Isaksson
 */
public class ESwingPanel extends EPanel {
    /**
     * Creates a new instance
     * @param layoutManager The layout manager to use
     */
    public ESwingPanel(LayoutManager layoutManager) {
        super(layoutManager!=null?new JPanel(layoutManager):new JPanel());
    }
}
