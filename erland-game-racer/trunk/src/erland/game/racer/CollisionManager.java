package erland.game.racer;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;
import java.util.ListIterator;

public class CollisionManager {
    Vector objects;

    public CollisionManager()
    {
        objects = new Vector();
    }
    public void addObject(CollisionObjectInterface object)
    {
        if(!objects.contains(object)) {
            objects.add(object);
        }
    }
    public void removeObject(CollisionObjectInterface object)
    {
        objects.remove(object);
    }

    protected void handleCollision(CollisionObjectInterface o1,CollisionObjectInterface o2)
    {
        Rectangle rc1 = o1.getBounds();
        Rectangle rc2 = o2.getBounds();
        if(rc1.intersects(rc2) || rc1.contains(rc2) || rc2.contains(rc1)) {
            o1.handleCollision(o2);
            o2.handleCollision(o1);
        }
    }

    public void handleCollisions()
    {
        for(int i=0;i<objects.size()-1;i++) {
            CollisionObjectInterface o1 = (CollisionObjectInterface)objects.get(i);
            for(int j=i+1;j<objects.size();j++) {
                CollisionObjectInterface o2 = (CollisionObjectInterface)objects.get(j);
                handleCollision(o1,o2);
            }
        }
    }
}
