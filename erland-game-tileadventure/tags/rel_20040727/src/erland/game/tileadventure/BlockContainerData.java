package erland.game.tileadventure;

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

public abstract class BlockContainerData implements IrregularBlockContainerInterface {
    /**
     * Horizontal scrolling offset in pixels
     */
    private int scrollingOffsetX;
    /**
     * Vertical scrolling offset in pixels
     */
    private int scrollingOffsetY;
    /**
     * The X offset in pixels
     */
    protected int offsetX;
    /**
     * The Y offset in pixels
     */
    protected int offsetY;
    /**
     * Number of horizontal blocks
     */
    protected int sizeX;
    /**
     * Number of vertical blocks
     */
    protected int sizeY;
    /**
     * Number of height blocks
     */
    protected int sizeZ;
    /**
     * The horizontal size of a single block in pixels
     */
    protected int squareSizeX;
    /**
     * The vertical size of a single block in pixels
     */
    protected int squareSizeY;
    /**
     * The height of a single block in pixels
     */
    protected int squareSizeZ;
    /**
     * Number of visible horizontal blocks
     */
    protected int visibleSizeX;
    /**
     * Number of visible vertical blocks
     */
    protected int visibleSizeY;

    public BlockContainerData() {
        scrollingOffsetX = 0;
        scrollingOffsetY = 0;
    }

    protected BlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY,int sizeZ, int squareSizeX,int squareSizeY,int squareSizeZ, int visibleSizeX, int visibleSizeY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.squareSizeX = squareSizeX;
        this.squareSizeY = squareSizeY;
        this.squareSizeZ = squareSizeZ;
        this.visibleSizeX = visibleSizeX;
        this.visibleSizeY = visibleSizeY;
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

    /**
     * Gets the horizontal scrolling offset
     * @return The X scrolling offset
     */
    public int getScrollingOffsetX() {
        return this.scrollingOffsetX;
    }

    /**
     * Gets the vertical scrolling offset
     * @return The Y scrolling offset
     */
    public int getScrollingOffsetY() {
        return this.scrollingOffsetY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
    public int getSizeZ() {
        return sizeZ;
    }
    public int getSquareSizeX() {
        return squareSizeX;
    }

    public int getSquareSizeY() {
        return squareSizeY;
    }

    public int getSquareSizeZ() {
        return squareSizeZ;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getDrawingSizeX()
    {
    	return visibleSizeX;
    }

    public int getDrawingSizeY()
    {
    	return visibleSizeY;
    }
}