package erland.game.isoadventure;
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

public class IsoBlockContainerData implements IrregularBlockContainerInterface {
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
     * The horizontal size of a single block in pixels
     */
    private int squareSizeX;

    /**
     * The vertical size of a single block in pixels
     */
    private int squareSizeY;

    /**
     * The height of a single block in pixels
     */
    private int squareSizeZ;

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

    public IsoBlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY,int squareSizeX,int squareSizeY,int squareSizeZ, int visibleSizeX, int visibleSizeY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.squareSizeX = squareSizeX;
        this.squareSizeY = squareSizeY;
        this.squareSizeZ = squareSizeZ;
        this.visibleSizeX = visibleSizeX;
        this.visibleSizeY = visibleSizeY;
        this.scrollingOffsetX = 0;
        this.scrollingOffsetY = 0;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
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

    public int getScrollingOffsetX() {
        return scrollingOffsetX;
    }

    public int getScrollingOffsetY() {
        return scrollingOffsetY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getDrawingPositionX(int x, int y, int z)
    {
    	return getOffsetX() - getScrollingOffsetX() + getPositionX(x,y,z);
    }
    public int getDrawingPositionY(int x, int y, int z)
    {
    	return getOffsetY() - getScrollingOffsetY() + getPositionY(x,y,z);
    }
    public int getDrawingPositionX(int x, int y, int z,float dx, float dy, float dz)
    {
    	return getOffsetX() - getScrollingOffsetX() + getPositionX(x,y,z,dx,dy,dz);
    }
    public int getDrawingPositionY(int x, int y, int z,float dx,float dy, float dz)
    {
    	return getOffsetY() - getScrollingOffsetY() + getPositionY(x,y,z,dx,dy,dz);
    }
    public int getPositionX(int x, int y, int z)
    {
    	return (getSquareSizeX()*getSizeX()/2)+(getSquareSizeX()/2)*x-(getSquareSizeX()/2)*y;
    }
    public int getPositionY(int x, int y, int z)
    {
        return (getSquareSizeY()/2)*x+(getSquareSizeY()/2)*y-getSquareSizeZ()*z;
    }
    public int getPositionX(int x, int y, int z, float dx, float dy, float dz)
    {
    	return getPositionX(x,y,z)+getPositionX((int)(dx*100),(int)(dy*100),(int)(dz*100))/100;
    }
    public int getPositionY(int x, int y, int z, float dx, float dy, float dz)
    {
        return getPositionY(x,y,z)+getPositionY((int)(dx*100),(int)(dy*100),(int)(dz*100))/100;
    }
    public int getDrawingSizeX()
    {
    	return visibleSizeX;
    }
    public int getDrawingSizeY()
    {
    	return visibleSizeY;
    }
    public boolean getVisible(int posX, int posY,int posZ)
    {
        if((getPositionX(posX,posY,posZ)+getSquareSizeX())>scrollingOffsetX && getPositionX(posX,posY,posZ)<(scrollingOffsetX+getDrawingSizeX())) {
            if((getPositionY(posX,posY,posZ)+getSquareSizeY())>scrollingOffsetY && getPositionY(posX,posY,posZ)<(scrollingOffsetY+getDrawingSizeY())) {
                return true;
            }
        }
        return false;
    }
    /**
	 * Get the horizontal scrolling size
	 * @return The horizontal scrolling offset (Pixel coordinates)
	 */
	public int getScrollingSizeX()
	{
		return getSizeX()*getSquareSizeX()+getSizeY()*getSquareSizeX();
	}
	/**
	 * Get the vertical scrolling size
	 * @return The vertical scrolling size (Pixel coordinates)
	 */
	public int getScrollingSizeY()
	{
		return getSizeX()*getSquareSizeY()+getSizeY()*getSquareSizeY();
	}

    public int getPixelDrawingPositionX(int x) {
        return getOffsetX()-getScrollingOffsetX()+x;
    }

    public int getPixelDrawingPositionY(int y) {
        return getOffsetY()-getScrollingOffsetY()+y;
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
}
