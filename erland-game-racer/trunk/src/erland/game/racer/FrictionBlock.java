package erland.game.racer;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

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
        rc.setLocation(offsetX+x*squareSize,offsetY+y*squareSize);
        rc.setSize(squareSize,squareSize);
        //TODO Remove next line, it was replaced to make it work with java 1.1
        //rc.setRect(offsetX+x*squareSize,offsetY+y*squareSize,squareSize,squareSize);
    }
    public void setPos(int x, int y)
    {
        this.x = x;
        this.y = y;
        rc.setLocation(offsetX+x*squareSize,offsetY+y*squareSize);
        rc.setSize(squareSize,squareSize);
        //TODO Remove next line, it was replaced to make it work with java 1.1
        //rc.setRect(offsetX+x*squareSize,offsetY+y*squareSize,squareSize,squareSize);
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
