package erland.game.tileadventure.rect;

import erland.game.tileadventure.IrregularBlockContainerInterface;
import erland.game.tileadventure.BlockContainerData;

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

public class RectBlockContainerData extends BlockContainerData {
    private int blockDrawingOffsetX;
    private int blockDrawingOffsetY;
    public RectBlockContainerData(int offsetX, int offsetY, int blockDrawingOffsetX, int blockDrawingOffsetY, int sizeX, int sizeY,int sizeZ, int squareSizeX,int squareSizeY,int squareSizeZ, int visibleSizeX, int visibleSizeY) {
        super(offsetX,offsetY,sizeX,sizeY,sizeZ, squareSizeX,squareSizeY,squareSizeZ,visibleSizeX,visibleSizeY);
    }

    public int getDrawingPositionX(int x, int y, int z)
    {
    	return getDrawingPositionX(x,y,z,0,0,0);
    }
    public int getDrawingPositionY(int x, int y, int z)
    {
        return getDrawingPositionY(x,y,z,0,0,0);
    }
    public int getDrawingPositionX(int x, int y, int z,float dx, float dy, float dz)
    {
    	return getOffsetX() + blockDrawingOffsetX - getScrollingOffsetX() + getPositionX(x,y,z,dx,dy,dz);
    }
    public int getDrawingPositionY(int x, int y, int z,float dx,float dy, float dz)
    {
    	return getOffsetY() + blockDrawingOffsetY - getScrollingOffsetY() + getPositionY(x,y,z,dx,dy,dz);
    }
    public int getPositionX(int x, int y, int z)
    {
    	return getRelativePositionX(x,y,z);
    }
    public int getPositionY(int x, int y, int z)
    {
        return getRelativePositionY(x,y,z);
    }
    private int getRelativePositionX(float dx, float dy, float dz) {
        return (int)(getSquareSizeX()*dx);
    }
    private int getRelativePositionY(float dx, float dy, float dz) {
        return (int)(getSquareSizeY()*dy);
    }
    public int getPositionX(int x, int y, int z, float dx, float dy, float dz)
    {
    	return getPositionX(x,y,z)+getRelativePositionX(dx,dy,dz);
    }
    public int getPositionY(int x, int y, int z, float dx, float dy, float dz)
    {
        return getPositionY(x,y,z)+getRelativePositionY(dx,dy,dz);
    }

    public boolean getVisible(int posX, int posY,int posZ)
    {
        if((getPositionX(posX,posY,posZ)+getSquareSizeX())>getScrollingOffsetX() && getPositionX(posX,posY,posZ)<(getScrollingOffsetX()+getDrawingSizeX())) {
            if((getPositionY(posX,posY,posZ)+getSquareSizeY())>getScrollingOffsetY() && getPositionY(posX,posY,posZ)<(getScrollingOffsetY()+getDrawingSizeY())) {
                return true;
            }
        }
        return false;
    }

    public int getPixelDrawingPositionX(int x, int y, int z) {
        return getDrawingPositionX(x,y,z);
    }

    public int getPixelDrawingPositionY(int x, int y, int z) {
        return getDrawingPositionY(x,y,z);
    }
    /**
	 * Get the horizontal scrolling size
	 * @return The horizontal scrolling offset (Pixel coordinates)
	 */
	public int getScrollingSizeX()
	{
		return getSizeX()*getSquareSizeX();
	}

    /**
	 * Get the vertical scrolling size
	 * @return The vertical scrolling size (Pixel coordinates)
	 */
	public int getScrollingSizeY()
	{
		return getSizeY()*getSquareSizeY();
	}
    public int getBlockPositionX(int x, int y, int z) {
        return (x+getScrollingOffsetX()-getOffsetX())/getSquareSizeX();
    }

    public int getBlockPositionY(int x, int y, int z) {
        return (y+getScrollingOffsetY()-getOffsetY())/getSquareSizeY();
    }
}
