package erland.game.tileadventure.isodiamond;

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

public class IsoDiamondBlockContainerData extends BlockContainerData {
    private int blockDrawingOffsetX;
    private int blockDrawingOffsetY;
    public IsoDiamondBlockContainerData(int offsetX, int offsetY, int blockDrawingOffsetX, int blockDrawingOffsetY, int sizeX, int sizeY,int sizeZ, int squareSizeX,int squareSizeY,int squareSizeZ, int visibleSizeX, int visibleSizeY) {
        super(offsetX,offsetY,sizeX,sizeY,sizeZ, squareSizeX,squareSizeY,squareSizeZ,visibleSizeX,visibleSizeY);
        this.blockDrawingOffsetX = blockDrawingOffsetX;
        this.blockDrawingOffsetY = blockDrawingOffsetY;
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
    	return ((getSquareSizeX()*getSizeX())>>1)-(getSquareSizeX()>>1)+getRelativePositionX(x,y,z);
    }
    public int getPositionY(int x, int y, int z)
    {
        return getRelativePositionY(x,y,z);
    }
    private int getRelativePositionX(float dx, float dy, float dz) {
        return (int)((getSquareSizeX()>>1)*dx-(getSquareSizeX()>>1)*dy);
    }
    private int getRelativePositionY(float dx, float dy, float dz) {
        return (int)((getSquareSizeY()>>1)*dx+(getSquareSizeY()>>1)*dy-getSquareSizeZ()*dz);
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
        return getOffsetX() - getScrollingOffsetX() + getPositionX(x,y,z);
    }

    public int getPixelDrawingPositionY(int x, int y, int z) {
        return getOffsetY() - getScrollingOffsetY() + getPositionY(x,y,z);
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
		return getSizeX()*getSquareSizeY()+getSizeY()*getSquareSizeY()+getSizeZ()*getSquareSizeZ();
	}

    public int getBlockPositionX(int x, int y, int z) {
        int realX = x+getScrollingOffsetX()-getOffsetX();
        int realY = y+getScrollingOffsetY()-getOffsetY();
        int zeroX = getPositionX(0,0,0,0,0,(float)z/getSquareSizeZ())+getSquareSizeX()/2;
        int zeroY = getPositionY(0,0,0,0,0,(float)z/getSquareSizeZ());

        int screenDeltaX = realX-zeroX;
        int screenDeltaY = realY-zeroY;

        int worldDeltaX = (screenDeltaX+screenDeltaY*2)/getSquareSizeX();
        int worldDeltaY = (screenDeltaY*2-screenDeltaX)/getSquareSizeX();

        int posX = worldDeltaX;
        int posY = worldDeltaY;

        return posX;
    }

    public int getBlockPositionY(int x, int y, int z) {
        int realX = x+getScrollingOffsetX()-getOffsetX();
        int realY = y+getScrollingOffsetY()-getOffsetY();
        int zeroX = getPositionX(0,0,0,0,0,(float)z/getSquareSizeZ())+getSquareSizeX()/2;
        int zeroY = getPositionY(0,0,0,0,0,(float)z/getSquareSizeZ());

        int screenDeltaX = realX-zeroX;
        int screenDeltaY = realY-zeroY;

        int worldDeltaX = (screenDeltaX+screenDeltaY*2)/getSquareSizeX();
        int worldDeltaY = (screenDeltaY*2-screenDeltaX)/getSquareSizeX();

        int posX = worldDeltaX;
        int posY = worldDeltaY;

        return posY;
    }
}
