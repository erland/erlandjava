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
public class BlockContainerData
	implements BlockContainerInterface
{
	/**
	 * The X offset in pixels
	 */
	private int offsetX;
	
	/**
	 * The Y offset in pixels
	 */
	private int offsetY;
	
	/**
	 * Number of horizontal blocks
	 */
	private int sizeX;
	
	/**
	 * Number of vertical blocks
	 */
	private int sizeY;

	/**
	 * The size of a single block in pixels
	 */
	private int squareSize;

    /**
     * Number of visible horizontal blocks
     */
    private int visibleSizeX;

    /**
     * Number of visible vertical blocks
     */
    private int visibleSizeY;

    /**
     * Horizontal scrolling offset in pixels
     */
    private int scrollingOffsetX;

    /**
     * Vertical scrolling offset in pixels
     */
    private int scrollingOffsetY;

	/**
	 * Creates a new object not scrollable
	 * @param offsetX The X offset in pixels
	 * @param offsetY The Y offset in pixels
	 * @param sizeX Number of horizontal blocks
	 * @param sizeY Number of vertical blocks
	 * @param squareSize Size of a single block in pixels
	 */
	public BlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY, int squareSize)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.squareSize = squareSize;
        this.visibleSizeX = sizeX;
        this.visibleSizeY = sizeY;
        this.scrollingOffsetX = 0;
        this.scrollingOffsetY = 0;
	}
    /**
	 * Creates a new object
	 * @param offsetX The X offset in pixels
	 * @param offsetY The Y offset in pixels
	 * @param sizeX Number of horizontal blocks
	 * @param sizeY Number of vertical blocks
	 * @param squareSize Size of a single block in pixels
     * @param visibleSizeX Number of visible horizontal blocks
     * @param visibleSizeY Number of visible vertical blocks
	 */
	public BlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY, int squareSize,int visibleSizeX, int visibleSizeY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.squareSize = squareSize;
        this.visibleSizeX = visibleSizeX;
        this.visibleSizeY = visibleSizeY;
        this.scrollingOffsetX = 0;
        this.scrollingOffsetY = 0;
	}

	public int getOffsetX()
	{
		return offsetX;
	}
	public int getOffsetY()
	{
		return offsetY;
	}
	public int getSizeX()
	{
		return sizeX;
	}
	public int getSizeY()
	{
		return sizeY;
	}
	public int getSquareSize()
	{
		return squareSize;
	}
    public int getDrawingPositionX(int x)
    {
    	return getOffsetX() - getScrollingOffsetX() + getSquareSize()*x;
    }
    public int getDrawingPositionY(int y)
    {
    	return getOffsetY() - getScrollingOffsetY() + getSquareSize()*y;
    }
    public int getPositionX(int x)
    {
    	return getSquareSize()*x;
    }
    public int getPositionY(int y)
    {
    	return getSquareSize()*y;
    }
    public int getDrawingSizeX()
    {
    	return getSquareSize()*visibleSizeX;
    }
    public int getDrawingSizeY()
    {
    	return getSquareSize()*visibleSizeY;
    }
    public boolean getVisible(int posX, int posY)
    {
        if((getPositionX(posX)+getSquareSize())>scrollingOffsetX && getPositionX(posX)<(scrollingOffsetX+getDrawingSizeX())) {
            if((getPositionY(posY)+getSquareSize())>scrollingOffsetY && getPositionY(posY)<(scrollingOffsetY+getDrawingSizeY())) {
                return true;
            }
        }
        return false;
    }
	/**
	 * Sets the horizontal drawing offset
	 * @param offsetX The new X drawing offset
	 */
	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}
	/**
	 * Sets the horizontal drawing offset
	 * @param offsetY The new Y drawing offset
	 */
	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}

    /**
	 * Sets the horizontal scrolling offset
	 * @param offsetX The new X scrolling offset
	 */
	public void setScrollingOffsetX(int offsetX)
	{
		this.scrollingOffsetX = offsetX;
	}
	/**
	 * Sets the horizontal scrolling offset
	 * @param offsetY The new Y scrolling offset
	 */
	public void setScrollingOffsetY(int offsetY)
	{
		this.scrollingOffsetY = offsetY;
	}
	public int getScrollingOffsetX()
	{
		return scrollingOffsetX;
	}

	public int getScrollingOffsetY()
	{
		return scrollingOffsetY;
	}

    /**
	 * Get the horizontal scrolling size
	 * @return The horizontal scrolling offset (Pixel coordinates)
	 */
	public int getScrollingSizeX()
	{
		return getSizeX()*getSquareSize();
	}
	/**
	 * Get the vertical scrolling size
	 * @return The vertical scrolling size (Pixel coordinates)
	 */
	public int getScrollingSizeY()
	{
		return getSizeY()*getSquareSize();
	}

    public int getPixelDrawingPositionX(int x) {
        return getOffsetX()-getScrollingOffsetX()+x;
    }

    public int getPixelDrawingPositionY(int y) {
        return getOffsetY()-getScrollingOffsetY()+y;
    }
}