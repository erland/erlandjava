package erland.game.boulderdash;
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
import erland.game.*;

/**
 * Represents a block on the game area
 */
 
class BlockDiamond extends Block
{
	/** Indicates that the block is moving */
	protected boolean moving;
	
	/** Indicates the block moving direction of the block, see {@link erland.game#Direction} */
	protected int movingDirection;
	
	/** Indicates the moving progress in the direction specified in {@link #movingDirection} */
	protected float movingProgress;

	/** Indicates the speed the block is moved with */
	protected float moveSpeed;

	/** Indicates the fall height when the block is falling down */
	protected int fallHeight;

	/** Falling speed */
	protected static float FALL_SPEED = 1.5f;

	/** Image of the block */
	protected Image img;
	
	public void init(GameEnvironmentInterface environment, BoulderDashContainerInterface c, BlockContainerInterface cont, int x, int y)
	{
		super.init(environment,c,cont,x,y);
		img = environment.getImageHandler().getImage("diamond.gif");
	}

	public void update()
	{
		if(moving) {
			setRedraw(true);
			movingProgress+=moveSpeed;
			if(movingProgress>=cont.getSquareSize()) {
				int newX = getMovingPosX();
				int newY = getMovingPosY();
				if(movingDirection==Direction.DOWN) {
					fallHeight++;
				}
				moving = false;
				movingProgress=0;
				c.setBlockPos(x,y,newX,newY);
			}
		}else {
			setRedraw(false);
			fallHeight=0;
		}
		if(!moving) {
			if(c.isFree(x,y+1)) {
				c.moveBlock(x,y,Direction.DOWN,FALL_SPEED);
			}else if(c.isFree(x-1,y) && c.isFree(x-1,y+1) && c.isSlippery(x,y+1)) {
				c.moveBlock(x,y,Direction.LEFT,FALL_SPEED);
			}else if(c.isFree(x+1,y) && c.isFree(x+1,y+1) && c.isSlippery(x,y+1)) {
				c.moveBlock(x,y,Direction.RIGHT,FALL_SPEED);
			}
		}
	}
	
	public void draw(Graphics g)
	{
		int dx = cont.getDrawingPositionX(x);
		int dy = cont.getDrawingPositionY(y);
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					dx-=movingProgress;
					break;
				case Direction.RIGHT:
					dx+=movingProgress;
					break;
				case Direction.UP:
					dy-=movingProgress;
					break;
				case Direction.DOWN:
					dy+=movingProgress;
					break;
				default:
					break;
			}
		}
		g.drawImage(img,dx,dy,null);
		
		//g.setColor(Color.lightGray);
		//g.fillRect(dx,dy,cont.getSquareSize(),cont.getSquareSize());
	}

	public boolean isDigThrough()
	{
		return true;
	}
	
	public boolean dig(int direction,float speed)
	{
		c.increaseDiamonds();
		c.delBlock(this);
		return true;
	}
	public boolean move(int direction,float speed)
	{
		if(!moving) {
			if(direction==Direction.DOWN) {
				if(c.isFree(x,y+1)) {
					moving= true;
					moveSpeed = speed;
					movingDirection = Direction.DOWN;
					return true;
				}
			}else if(direction==Direction.LEFT) {
				if(c.isFree(x-1,y)) {
					moving= true;
					moveSpeed = speed;
					movingDirection = Direction.LEFT;
					return true;
				}
			}else if(direction==Direction.RIGHT) { 
				if(c.isFree(x+1,y)) {
					moving = true;
					moveSpeed = speed;
					movingDirection = Direction.RIGHT;
					return true;
				}
			}
		}
		return false;
	}
	public boolean isMoving()
	{
		return moving;
	}
	public boolean isSlippery()
	{
		return true;
	}

	public int getMovingPosX()
	{
		int newX = x;
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					newX--;
					break;
				case Direction.RIGHT:
					newX++;
					break;
				default:
					break;
			}
		}
		return newX;
	}

	public int getMovingPosY()
	{
		int newY = y;
		if(moving) {
			switch(movingDirection) {
				case Direction.UP:
					newY--;
					break;
				case Direction.DOWN:
					newY++;
					break;
				default:
					break;
			}
		}
		return newY;
	}

}
