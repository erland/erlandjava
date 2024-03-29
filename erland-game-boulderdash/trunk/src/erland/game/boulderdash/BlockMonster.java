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
 
class BlockMonster extends Block
{
	/** Indicates that the block is moving */
	protected boolean moving;
	
	/** Indicates the block moving direction of the block, see {@link erland.game#Direction} */
	protected int movingDirection;
	
	/** Indicates the moving progress in the direction specified in {@link #movingDirection} */
	protected float movingProgress;
	
	/** Indicates the speed the block is moved with */
	protected float moveSpeed;
	
	/** Image of the block */
	protected Image img;
	
	/** Falling speed */
	protected float speed = 1.5f;
	
	public void init(GameEnvironmentInterface environment, BoulderDashContainerInterface c, BlockContainerInterface cont, int x, int y)
	{
		super.init(environment,c,cont,x,y);
		img = environment.getImageHandler().getImage("monster.gif");
	}

	public void update()
	{
		if(moving) {
			setRedraw(true);
			movingProgress+=moveSpeed;
			if(movingProgress>=cont.getSquareSize()) {
				moving = false;
				movingProgress=0;
				int newX = x;
				int newY = y;
				switch(movingDirection) {
					case Direction.LEFT:
						newX--;
						break;
					case Direction.RIGHT:
						newX++;
						break;
					case Direction.UP:
						newY--;
						break;
					case Direction.DOWN:
						newY++;
						break;
					default:
						break;
				}
				c.setBlockPos(x,y,newX,newY);
			}
		}else {
			setRedraw(false);
		}
		if(!moving) {
			int firstChoiceDir = c.getPlayerDirection(x,y);
			if(firstChoiceDir==Direction.opposite(movingDirection)) {
				firstChoiceDir=movingDirection;
			}
			if(!killAndMove(firstChoiceDir)) {
				if(!c.moveBlock(x,y,firstChoiceDir,speed)) {
					switch(movingDirection) {
						case Direction.LEFT:
							if(!killAndMove(Direction.LEFT)) {
								if(c.moveBlock(x,y,Direction.LEFT,speed)) {
								}else if(c.moveBlock(x,y,Direction.UP,speed)) {
								}else if(c.moveBlock(x,y,Direction.DOWN,speed)) {
								}else if(c.moveBlock(x,y,Direction.RIGHT,speed)) {
								}else {
									movingDirection = Direction.UP;
								}
							}
							break;
						case Direction.RIGHT:
							if(!killAndMove(Direction.RIGHT)) {
								if(c.moveBlock(x,y,Direction.RIGHT,speed)) {
								}else if(c.moveBlock(x,y,Direction.DOWN,speed)) {
								}else if(c.moveBlock(x,y,Direction.UP,speed)) {
								}else if(c.moveBlock(x,y,Direction.LEFT,speed)) {
								}else {
									movingDirection = Direction.DOWN;
								}
							}
							break;
						case Direction.UP:
							if(!killAndMove(Direction.UP)) {
								if(c.moveBlock(x,y,Direction.UP,speed)) {
								}else if(c.moveBlock(x,y,Direction.RIGHT,speed)) {
								}else if(c.moveBlock(x,y,Direction.LEFT,speed)) {
								}else if(c.moveBlock(x,y,Direction.DOWN,speed)) {
								}else {
									movingDirection = Direction.RIGHT;
								}
							}
							break;
						case Direction.DOWN:
							if(!killAndMove(Direction.DOWN)) {
								if(c.moveBlock(x,y,Direction.DOWN,speed)) {
								}else if(c.moveBlock(x,y,Direction.LEFT,speed)) {
								}else if(c.moveBlock(x,y,Direction.RIGHT,speed)) {
								}else if(c.moveBlock(x,y,Direction.UP,speed)) {
								}else {
									movingDirection = Direction.LEFT;
								}
							}
							break;
						default:
							break;
					}
				}
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
		g.setColor(Color.red);
		g.drawImage(img,dx,dy,null);
		//g.fillRect(dx,dy,cont.getSquareSize(),cont.getSquareSize());
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
			}else if(direction==Direction.UP) { 
				if(c.isFree(x,y-1)) {
					moving = true;
					moveSpeed = speed;
					movingDirection = Direction.UP;
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
	
	/**
	 * Kill block in specified direction if possible and change moving direction to that one
	 * @param direction Direction in which we should check block
	 * @return true/false (Block destroyed/Block not destroyed)
	 */
	protected boolean killAndMove(int direction)
	{
		switch(direction) {
			case Direction.LEFT:
				if(c.isKillable(x-1,y)) {
					c.destroyBlock(x-1,y,1);
					c.moveBlock(x,y,Direction.LEFT,speed);
					return true;
				}
				break;
			case Direction.RIGHT:
				if(c.isKillable(x+1,y)) {
					c.destroyBlock(x+1,y,1);
					c.moveBlock(x,y,Direction.RIGHT,speed);
					return true;
				}
				break;
			case Direction.UP:
				if(c.isKillable(x,y-1)) {
					c.destroyBlock(x,y-1,1);
					c.moveBlock(x,y,Direction.UP,speed);
					return true;
				}
				break;
			case Direction.DOWN:
				if(c.isKillable(x,y+1)) {
					c.destroyBlock(x,y+1,1);
					c.moveBlock(x,y,Direction.DOWN,speed);
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}
	public boolean isDestroyable()
	{
		return true;
	}
	public boolean destroy()
	{
		c.delBlock(this);
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
