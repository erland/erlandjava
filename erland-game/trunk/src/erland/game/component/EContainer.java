package erland.game.component;

import java.awt.*;

/**
 * Represents a Container object
 * @author Erland Isaksson
 */
public class EContainer extends EComponent {
    /**
     * Initialize a new instance
     * @param container The container of the new object
     */
    protected EContainer(Container container) {
        super(container);
        me = container;
    }
    /**
     * Get the ontainer of this EContainer object
     * @return
     */
    public Container getContainer() {
        return (Container)getComponent();
    }
}
