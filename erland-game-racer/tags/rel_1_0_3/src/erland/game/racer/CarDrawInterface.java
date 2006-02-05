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
 * This interface defines the methods needed for a Car that should be drawn
 */
public interface CarDrawInterface {
    /**
     * Get the x pixel position of the car
     * @return The x pixel position
     */
    int getPosX();
    /**
     * Get the y pixel position of the car
     * @return The y pixel position
     */
    int getPosY();
    /**
     * Draw the the specified level of the car on the specified Graphics object
     * @param g Graphics object to draw on
     * @param level The level of the car which should be drawn
     */
    void draw(Graphics g, int level);
}
