package erland.game.crackout;
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
 * Represents a single bat
 */
class Bat
	implements CollisionRect
{
	/** X position of the bat */
	protected int x;
	/** Y position of the bat */
	protected int y;
	/** Minimum x position of some part of the bat */
	protected int leftLimit;
	/** Maximum x position of some part of the bat */
	protected int rightLimit;
	/** Current width of the bat */
	protected int sizeX;
	/** Height of the bat */
	protected int sizeY;
	/** Speed of the bat */
	protected int speed;
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Requested width of the bat */
	protected int wantedSizeX;
	
	/**
	 * Initialize bat
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param leftLimit Minimum x position of some part of the bat
	 * @param rightLimit Maximum x position of some part of the bat
	 * @param x Initial X position
	 * @param y Initial Y position
	 * @param sizeX Width of the bat
	 * @param sizeY Height of the bat
	 * @param speed Speed of the bat
	 */
	public void init(int offsetX, int offsetY, int leftLimit, int rightLimit, int x, int y, int sizeX, int sizeY, int speed)
	{
		this.x = x;
		this.y = y;
		this.leftLimit = leftLimit;
		this.rightLimit = rightLimit;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.speed = speed;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.wantedSizeX = sizeX;
	}
	
	public int left()
	{
		return x;
	}
	
	public int right()
	{
		return x+sizeX;
	}
	
	public int top()
	{
		return y;
	}
	
	public int bottom()
	{
		return y+sizeY;
	}
		
	/**
	 * Move the bat one step right
	 * @return true/false (Success/Failure)
	 */
	public boolean moveRight()
	{
		x+=speed;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
			return false;
		}
		return true;
	}
	
	/**
	 * Move the bat one step left
	 * @return true/false (Success/Failure)
	 */
	public boolean moveLeft()
	{
		x-=speed;
		if(x<leftLimit) {
			x=leftLimit;
			return false;
		}
		return true;
	}
	
	/**
	 * Draw the bat
	 * @param g The Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(wantedSizeX!=sizeX) {
			changeSize();
		}
		g.setColor(Color.gray);
		g.fillRect(offsetX+x,offsetY+y,sizeX,sizeY);
		g.setColor(new Color(0x666666));
		g.drawRect(offsetX+x+1,offsetY+y+1,sizeX-3,sizeY-3);
		g.setColor(new Color(0x444444));
		g.drawRect(offsetX+x,offsetY+y,sizeX-1,sizeY-1);
	}
	public void handleCollision(ActionInterface a) {
	}
	
	/**
	 * Set the speed of the bat
	 * @param speed The new speed of the bat
	 */
	public void setSpeed(int speed)
	{
		if(speed>=1) {
			if(speed>16) {
				speed=16;
			}
			this.speed = speed;
		}
	}
	
	/**
	 * Get the current speed of the bat
	 * @return The current speed of the bat
	 */
	public int getSpeed()
	{
		return this.speed;
	}
	
	/**
	 * Get the current width of the bat
	 * @return The current width of the bat
	 */
	public int getSizeX()
	{
		return this.sizeX;
	}
	
	/**
	 * Change the width of the bat if the requested width
	 * is different than the current width
	 */
	protected void changeSize()
	{
		if(wantedSizeX>sizeX) {
			if(wantedSizeX>(sizeX+1)) {
				sizeX+=2;
				x--;
			}else {
				sizeX++;
			}
		}else if(wantedSizeX<sizeX) {
			if(wantedSizeX<(sizeX-1)) {
				sizeX-=2;
				x++;
			}else {
				sizeX--;
			}
		}
		x = x-(sizeX-this.sizeX)/2;
		this.sizeX = sizeX;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
		}
		if(x<leftLimit) {
			x=leftLimit;
		}
	}
	
	/**
	 * Set the current and requested width of the bat
	 * @param sizeX The new width of the bat
	 */
	public void setSizeXFast(int sizeX)
	{
		this.sizeX = sizeX;
		wantedSizeX = sizeX;
	}

	/**
	 * Set the requested width of the bat, the bat will shrink/grow to
	 * this width during the next redraws
	 * @param sizeX The new requested width of the bat
	 */
	public void setSizeX(int sizeX)
	{
		wantedSizeX = sizeX;
	}
	
	/**
	 * Change the minimum and maximum x position of some part of the bat
	 * @param left The new minimum x position
	 * @param right The new maximum x position
	 */
	public void setLimit(int left, int right)
	{
		leftLimit = left;
		rightLimit = right;
		if(x>(rightLimit-sizeX)) {
			x = rightLimit-sizeX;
		}
		if(x<leftLimit) {
			x=leftLimit;
		}
	}
}
