package erland.game.racer;

import java.awt.*;
import java.util.Vector;

/**
 * A collision manager that checks for collision between {@link CollisionObjectInterface} objects
 * and apply collision effects on these
 */
public class CollisionManager {
    /** Managed collision objects */
    Vector objects;

    /** Creates a collision manager object */
    public CollisionManager()
    {
        objects = new Vector();
    }
    /**
     * Add a collision object to the manager
     * @param object The collision object to add
     */
    public void addObject(CollisionObjectInterface object)
    {
        if(!objects.contains(object)) {
            objects.addElement(object);
        }
    }
    /**
     * Remove an earlier added collision object from the manager
     * @param object The collsion object to remove
     */
    public void removeObject(CollisionObjectInterface object)
    {
        objects.removeElement(object);
    }

    /**
     * Check if two managed collision objects is colliding and if they are apply collision effects
     * @param o1 The first collision object to check
     * @param o2 The second collision object to check
     */
    protected void handleCollision(CollisionObjectInterface o1,CollisionObjectInterface o2)
    {
        Rectangle rc1 = o1.getBounds();
        Rectangle rc2 = o2.getBounds();
        if(rc1.intersects(rc2) || CommonRectangle.contains(rc1,rc2) || CommonRectangle.contains(rc2,rc1)) {
            o1.handleCollision(o2);
            o2.handleCollision(o1);
            o1.applyCollisions();
            o2.applyCollisions();
        }
    }

    /**
     * Check collissions between all managed collision objects and apply collision effects on those
     * objects that are colliding
     */
    public void handleCollisions()
    {
        for(int i=0;i<objects.size();i++) {
            CollisionObjectInterface o1 = (CollisionObjectInterface)objects.elementAt(i);
            for(int j=i+1;j<objects.size();j++) {
                CollisionObjectInterface o2 = (CollisionObjectInterface)objects.elementAt(j);
                handleCollision(o1,o2);
            }
        }
    }
}
