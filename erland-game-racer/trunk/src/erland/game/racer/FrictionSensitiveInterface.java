package erland.game.racer;

import java.awt.*;

/**
 * Defines all methods that a friction sensitive object needs to implement
 */
public interface FrictionSensitiveInterface {
    /**
     * Get the bounding rectangle
     * @return Bounding rectangle
     */
    public Rectangle getBounds();
    /**
     * Apply the specified friction on the friction sensitive object
     * @param friction The friction to apply
     */
    public void setFriction(double friction);
}
