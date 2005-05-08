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
