package erland.game.component;

import java.awt.*;

/**
 * Represents a Component object
 * @author Erland Isaksson
 */
public class EComponent {
    /** The Component object of this component */
    protected Component me;
    /**
     * Initializes a new instance
     * @param component The Component object for this component
     */
    protected EComponent(Component component) {
        me = component;
    }
    /**
     * Get the Component object for this component
     * @return The Component object for this component
     */
    public Component getComponent() {
        return this.me;
    }
}
