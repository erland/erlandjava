package erland.game.component.awt;

import erland.game.component.EPanel;

import java.awt.*;

/**
 * An AWT implementation for the EPanel
 * @author Erland Isaksson
 */
public class EAWTPanel extends EPanel {
    /**
     * Creates a new instance
     */
    public EAWTPanel() {
        super(new Container());
    }
    /**
     * Creates a new instance
     * @param layoutManager The layout manager to use
     */
    public EAWTPanel(LayoutManager layoutManager) {
        super(new Container());
        getContainer().setLayout(layoutManager);
    }
}
