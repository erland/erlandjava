package erland.game.tileadventure;

import erland.game.BlockContainerInterface;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public interface IrregularBlockContainerInterface {
    int getSizeX();

    int getSizeY();

    int getSizeZ();

    int getSquareSizeX();

    int getSquareSizeY();

    int getSquareSizeZ();

    int getOffsetX();

    int getOffsetY();

    int getDrawingPositionX(int x, int y, int z);

    int getDrawingPositionY(int x, int y, int z);

    int getDrawingPositionX(int x, int y, int z, float dx, float dy, float dz);

    int getDrawingPositionY(int x, int y, int z, float dx, float dy, float dz);

    int getPositionX(int x, int y, int z);

    int getPositionY(int x, int y, int z);

    int getDrawingSizeX();

    int getDrawingSizeY();

    boolean getVisible(int posX, int posY, int posZ);

    int getScrollingSizeX();

    int getScrollingSizeY();

    int getPixelDrawingPositionX(int x, int y, int z);

    int getPixelDrawingPositionY(int x, int y, int z);

    int getPositionX(int x, int y, int z, float dx, float dy, float dz);

    int getPositionY(int x, int y, int z, float dx, float dy, float dz);

    int getScrollingOffsetX();

    int getScrollingOffsetY();

    /**
     * Get the x block coordinate for a specific pixel drawing coordinate.
     * The scrollling offset is used when calculating the block coordinate
     * @param x The x pixel coordinate
     * @param y The y pixel coordinate
     * @param z THe z pixel coordinate
     * @return The x block cordinate
     */
    int getBlockPositionX(int x, int y, int z);

    /**
     * Get the y block coordinate for a specific pixel drawing coordinate
     * The scrollling offset is used when calculating the block coordinate
     * @param x The x pixel coordinate
     * @param y The y pixel coordinate
     * @param z The z pixel coordinate
     * @return The y block cordinate
     */
    int getBlockPositionY(int x, int y, int z);
}
