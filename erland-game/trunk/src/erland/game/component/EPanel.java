package erland.game.component;

import erland.game.component.awt.EAWTPanel;
import erland.game.component.swing.ESwingPanel;

import java.awt.*;

/**
 * Represents a Panel object
 * @author Erland Isaksson
 */
public class EPanel extends EContainer {
    /**
     * Initiates a new instance
     * @param container The container of the new instance
     */
    protected EPanel(Container container) {
        super(container);
    }
    /**
     * Creates a new EPanel using the selected implementation
     * @return The newly created EPanel object
     */
    public static EPanel create() {
        return EPanel.create(null);
    }
    /**
     * Creates a new EPanel using the specified layout manager with
     * the selected implementation
     * @param layoutManager The layout manager to use
     * @return The newly created EPanel object
     */
    public static EPanel create(LayoutManager layoutManager) {
        if(EComponentMode.isApplet()) {
            return new EAWTPanel(layoutManager);
        }else {
            return new ESwingPanel(layoutManager);
        }
    }
}
