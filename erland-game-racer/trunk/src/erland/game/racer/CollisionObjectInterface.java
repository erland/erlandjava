package erland.game.racer;

import java.awt.*;

/**
 * Defines all methods that an object collisions
 * should be checked and applied on needs to implement
 */
public interface CollisionObjectInterface {
    /**
     * Get the bounding rectangle of the collision object
     * @return The bounding rectangle
     */
    public Rectangle getBounds();
    //public Line2D[] getLines();
    //public void collision();
    //public void collision(Line2D line);

    /**
     * Calculate and store new speed and direction due to collision with
     * the specified object
     * @param object The object that is colliding with this object
     */
    public void handleCollision(CollisionObjectInterface object);
    /**
     * Apply the speed and direction calculated in {@link #handleCollision(CollisionObjectInterface)} due to collision
     */
    public void applyCollisions();
    /**
     * Get the moving direction of this object
     * @return The moving direction
     */
    public double getCollisionAngle();
    /**
     * Get the moving speed of this object
     * @return The moving speed
     */
    public double getCollisionSpeed();
    /**
     * Get the weight of this object
     * @return The weight of this object
     */
    public double getCollisionWeight();
}
