package erland.game.racer;

import java.awt.*;
import java.awt.geom.Line2D;

public interface CollisionObjectInterface {
    public Rectangle getBounds();
    //public Line2D[] getLines();
    //public void collision();
    //public void collision(Line2D line);
    public void handleCollision(CollisionObjectInterface object);
    public double getCollisionAngle();
    public double getCollisionSpeed();
    public double getCollisionWeight();
}
