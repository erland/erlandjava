package erland.game;
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

/**
 * Represents a container for blocks
 * @author Erland Isaksson
 */
public interface BlockContainerInterface
{
	/**
	 * Get the X offset which should be used when drawing
	 * @return The X offset in pixels
	 */
	public int getOffsetX();
	/**
	 * Get the Y offset which should be used when drawing
	 * @return The Y offset in pixels
	 */
	public int getOffsetY();
	/**
	 * Get the number of visible horizontal blocks
	 * @return The number of horizontal blocks
	 */
	public int getSizeX();
	/**
	 * Get the number of visible vertical blocks
	 * @return The number of vertical blocks
	 */
	public int getSizeY();

	/**
	 * Get the size of a single block
	 * @return The size of a single block in pixels
	 */
	public int getSquareSize();

	/**
     * Get the drawing position for a specific block coordinate
     * @param x Block x position (Square coordinate)
     * @return x drawing position in pixels
     */
    public int getDrawingPositionX(int x);

	/**
     * Get the drawing position for a specific block coordinate
     * @param y Block y position (Square coordinate)
     * @return y drawing position in pixels
     */
    public int getDrawingPositionY(int y);

    /**
     * Get the drawing position for a specific pixel coordinate
     * @param x Object x position (Pixel coordinate)
     * @return x drawing position in pixels
     */
    public int getPixelDrawingPositionX(int x);

	/**
     * Get the drawing position for a specific pixel coordinate
     * @param y Object y position (Pixel coordinate)
     * @return y drawing position in pixels
     */
    public int getPixelDrawingPositionY(int y);

	/**
     * Get the position for a specific block coordinate
     * @param x Block x position (Square coordinate)
     * @return x position in pixels
     */
    public int getPositionX(int x);

	/**
     * Get the position for a specific block coordinate
     * @param y Block y position (Square coordinate)
     * @return y position in pixels
     */
    public int getPositionY(int y);

	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    public int getDrawingSizeX();

	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    public int getDrawingSizeY();

    /**
     * Checks if a specific block is within the visible area
     * @param posX X position of the block to check (Square coordinate)
     * @param posY Y position of the block to check (Square coordinate)
     * @return true/false (Visible/Invisible)
     */
    public boolean getVisible(int posX, int posY);

    /**
	 * Get the horizontal scrolling offset
	 * @return The horizontal scrolling offset
	 */
	public int getScrollingOffsetX();
	/**
	 * Sets the vertical scrolling offset
	 * @return The vertical scrolling offset
	 */
	public int getScrollingOffsetY();

    /**
	 * Get the horizontal scrolling size
	 * @return The horizontal scrolling offset (Pixel coordinates)
	 */
	public int getScrollingSizeX();

	/**
	 * Get the vertical scrolling size
	 * @return The vertical scrolling size (Pixel coordinates)
	 */
	public int getScrollingSizeY();
}
