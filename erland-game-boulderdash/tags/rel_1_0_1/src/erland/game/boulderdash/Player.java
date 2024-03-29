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
 
class Player extends Block
{
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object */
	protected BlockContainerInterface cont;

	/** Previous X position of the player */
	protected int oldX;

	/** Previous Y position of the player */
	protected int oldY;

    /** Previous X position of the player before the previous position */
    protected int prevOldX;

    /** Previous Y position of the player before the previous position */
    protected int prevOldY;

	/** Indicates that the block is moving */
	protected boolean moving;
	
	/** Indicates the moving direction of the player, see {@link erland.game#Direction} */
	protected int movingDirection;

	/** Indicates the moving progress in the direction specified in {link #movingDirection} */
	protected int movingProgress;
	
	/** Indicates if the player is alive or not */
	protected boolean alive;
	
	/** Indicates the moving speed */
	protected int movingSpeed;
	
	/** Image for the player */
	protected Image img;
	
	/**
	 * Move the player one step in the specified direction
	 * @param direction Direction to move the player in
	 * @return true/false (Success/Failure)
	 */
	public boolean move(int direction)
	{
		boolean result=false;
		switch(direction) {
			case Direction.LEFT:
				result = moveBlock(x-1,y,direction);
				break;
			case Direction.RIGHT:
				result = moveBlock(x+1,y,direction);
				break;
			case Direction.UP:
				result = moveBlock(x,y-1,direction);
				break;
			case Direction.DOWN:
				result = moveBlock(x,y+1,direction);
				break;
			default:
				result = false;
				break;
		}
		if(result) {
			moving = true;
			movingProgress = 0;
			movingDirection = direction;
		}
		return result;
	}

	/**
	 * Move a block
	 * @param x The x position of the block to move
	 * @param y The y position of the block to move
	 * @param direction The direction to move the block in
	 * @return true/false (Success/Failure)
	 */
	protected boolean moveBlock(int x, int y, int direction)
	{
		boolean result = false;
		if(c.isFree(x,y)) {
			movingSpeed = 2;
			result = true;
		}else if(c.isMovable(x,y)) {
			if(c.moveBlock(x,y,direction,1)) {
				result = true;
				movingSpeed = 1;
			}
		}else if(c.isDigThrough(x,y)) {
			if(c.digBlock(x,y,direction,2)) {
				result = true;
				movingSpeed = 2;
			}
		}
		return result;
	}

	/**
	 * Indicates if the block is currently moving
	 * @return true/false (Moving/Not moving)
	 */
	public boolean isMoving()
	{
		return moving;
	}
	
	/**
	 * Gets the current moving direction of the player
	 * @return The moving direction, see {@link erland.game#Direction}
	 */
	public int movingDirection()
	{
		if(isMoving()) {
			return movingDirection;
		}
		return Direction.UP;
	}
	
	/**
	 * Initialize player object
	 * @param c Boulderdash container interface
	 * @param environment Image handler interface
	 * @param cont Block container interface
	 * @param x X position of the block
	 * @param y Y position of the block
	 */
	public void init(GameEnvironmentInterface environment, BoulderDashContainerInterface c, BlockContainerInterface cont, int x, int y)
	{
		this.c = c;
		this.environment = environment;
		this.cont = cont;
		this.x = x;
		this.y = y;
        this.oldX =x ;
        this.oldY =y;
        this.prevOldX =x ;
        this.prevOldY =y;
		this.alive = true;
		this.moving = false;
		this.img = environment.getImageHandler().getImage("player.gif");
	}
	
	/**
	 * Destroy the player
	 */
	public boolean destroy()
	{
		alive = false;
		return true;
	}
	
	/**
	 * Check if the player is still alive
	 * @return true/false (Alive/Not alive)
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Update the player object
	 */
	public void update()
	{
        prevOldX = oldX;
        prevOldY = oldY;
		oldX = x;
		oldY = y;
		if(moving) {
			movingProgress+=movingSpeed;
			if(movingProgress>=cont.getSquareSize()) {
				movingProgress=0;
				moving=false;
				switch(movingDirection) {
					case Direction.LEFT:
						x--;
						break;
					case Direction.RIGHT:
						x++;
						break;
					case Direction.UP:
						y--;
						break;
					case Direction.DOWN:
						y++;
						break;
					default:
						break;
				}
			}
		}
	}
	
	/**
	 * Draw the player
	 * @param g The Graphics object to draw on
	 */
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
		//g.setColor(Color.cyan);
		//g.fillRect(dx+cont.getSquareSize()/4,dy+cont.getSquareSize()/4,cont.getSquareSize()/2,cont.getSquareSize()/2);
	}

	public void drawClear(Graphics g)
	{
        g.clearRect(cont.getDrawingPositionX(oldX),cont.getDrawingPositionY(oldY),
            cont.getSquareSize(),cont.getSquareSize());
        if(oldX!=prevOldX || oldY!=prevOldY) {
            g.clearRect(cont.getDrawingPositionX(prevOldX),cont.getDrawingPositionY(prevOldY),
                cont.getSquareSize(),cont.getSquareSize());
        }
        if(x!=prevOldX || y!=prevOldY) {
            g.clearRect(cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),
                cont.getSquareSize(),cont.getSquareSize());
        }

	}

	/**
	 * Get the last stable x position of the player
	 * @return The X position (block coordinate)
	 */
	public int getPosX()
	{
		return x;
	}

	/**
	 * Get the last stable y position of the player
	 * @return The Y position (block coordinate)
	 */
	public int getPosY()
	{
		return y;
	}


	/**
	 * Get the x position which the player i moving towards
	 * @return The X position (block coordinate)
	 */
	public int getMovingPosX()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					return x-1;
				case Direction.RIGHT:
					return x+1;
				case Direction.UP:
				case Direction.DOWN:
				default:
					return x;
			}
		}else {
			return x;
		}
	}

	/**
	 * Get the y position which the player i moving towards
	 * @return The Y position (block coordinate)
	 */
	public int getMovingPosY()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
				case Direction.RIGHT:
					return y;
				case Direction.UP:
					return y-1;
				case Direction.DOWN:
					return y+1;
				default:
					return y;
			}
		}else {
			return y;
		}
	}

	/**
	 * Get the x position which the player is drawed on
	 * @return The X position (pixel coordinate)
	 */
	public int getDrawingPosX()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					return cont.getDrawingPositionX(x)-movingProgress;
				case Direction.RIGHT:
					return cont.getDrawingPositionX(x)+movingProgress;
				case Direction.UP:
				case Direction.DOWN:
				default:
					return cont.getDrawingPositionX(x);
			}
		}else {
			return cont.getDrawingPositionX(x);
		}
	}

	/**
	 * Get the y position which the player drawed on
	 * @return The Y position (pixel coordinate)
	 */
	public int getDrawingPosY()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
				case Direction.RIGHT:
					return cont.getDrawingPositionY(y);
				case Direction.UP:
					return cont.getDrawingPositionY(y)-movingProgress;
				case Direction.DOWN:
					return cont.getDrawingPositionY(y)+movingProgress;
				default:
					return cont.getDrawingPositionY(y);
			}
		}else {
			return cont.getDrawingPositionY(y);
		}
	}
	
	/**
	 * Get percentage of move that has been completed
	 * @return Percentage of move that has been completed, a number between 0.0 and 1.0
	 */
	public float moveCompleted()
	{
		if(moving) {
			return (float)((float)movingProgress/(float)cont.getSquareSize());
		}else {
			return 1;
		}
	}
}