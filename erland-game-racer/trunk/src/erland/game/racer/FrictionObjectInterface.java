package erland.game.racer;

import java.awt.*;

/**
 * Defines all methods that a friction object needs to implement
 */
public interface FrictionObjectInterface {
    /**
     * Get the bounding rectangle of the object
     * @return Bounding rectangle
     */
    public Rectangle getBounds();
    /**
     * Get the friction of the fricton object
     * @return The friction
     */
    double getFriction();
}

