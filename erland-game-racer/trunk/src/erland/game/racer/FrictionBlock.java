package erland.game.racer;

import erland.util.Log;

import java.awt.*;

public class FrictionBlock implements FrictionObjectInterface, Cloneable {
    protected Rectangle rc;
    public int squareSize;
    public int offsetX;
    public int offsetY;
    public int x;
    public int y;
    protected double friction;
    public FrictionBlock(int squareSize)
    {
        this.squareSize = squareSize;
        rc = new Rectangle();
    }
    public void setFriction(double friction)
    {
        this.friction = friction;
    }
    public void setOffset(int offsetX, int offsetY)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        rc.setRect(offsetX+x*squareSize,offsetY+y*squareSize,squareSize,squareSize);
    }
    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
        rc.setRect(offsetX+x*squareSize,offsetY+y*squareSize,squareSize,squareSize);
    }
    public double getFriction() {
        return friction;
    }

    public Rectangle getBounds() {
        return rc;
    }

    public Object clone() throws CloneNotSupportedException
    {
        FrictionBlock b = (FrictionBlock)(super.clone());
        b.rc = new Rectangle(rc);
        return b;
    }
}
