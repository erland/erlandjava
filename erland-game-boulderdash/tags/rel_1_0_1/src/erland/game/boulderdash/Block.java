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
import erland.util.*;
import erland.game.*;

/**
 * Represents a block on the game area
 */
 
abstract class Block
	implements Cloneable
{
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object */
	protected BlockContainerInterface cont;
	
	/** X position of the block */
	protected int x;

	/** Y position of the block */
	protected int y;

	/** Indicates if the blocks needs to be redrawn */
	protected boolean redraw;

    /** Game environment */
    protected GameEnvironmentInterface environment;

	/**
	 * Checks if it is possible to dig through the block
	 * @return true/false (Possible to dig through/Not possible to dig through)
	 */
	public boolean isDigThrough()
	{
		return false;
	}
	
	/**
	 * Dig through block in specified direction and speed
	 * @param direction The direction in which the digging is done, see {@link erland.game#Direction}
	 * @param speed The dig speed
	 * @return true/false (Success/Failure)
	 */
	public boolean dig(int direction, float speed)
	{
		return false;
	}

	/**
	 * Checks if the block is slippery so blocks on top of it also slippery will slide
	 * @return true/false (Slippery/Not slippery)
	 */
	public boolean isSlippery()
	{
		return false;
	}

	/**
	 * Checks if the block is possible to destroy
	 * @return true/false (Destroyable/Not destroyable)
	 */
	public boolean isDestroyable()
	{
		return false;
	}

	/**
	 * Checks if this block is movable
	 * @return true/false (Movable/Not movable)
	 */
	public boolean isMovable()
	{
		return false;
	}
	
	/**
	 * Move the block one step in the specified direction
	 * @param direction Direction to move the block in
	 * @param speed The speed the moving is done with
	 * @return true/false (Success/Failure)
	 */
	public boolean move(int direction,float speed)
	{
		return false;
	}

	/**
	 * Destroys the block 
	 * @return true/false (Success/Failure)
	 */
	public boolean destroy()
	{
		return false;
	}

	/**
	 * Indicates if the block is currently moving
	 * @return true/false (Moving/Not moving)
	 */
	public boolean isMoving()
	{
		return false;
	}

	
	/**
	 * Get the x position of the block
	 * @return The x position of the block
	 */
	public int getPosX()
	{
		return x;
	}

	/**
	 * Get the y position of the block
	 * @return The y position of the block
	 */
	public int getPosY()
	{
		return y;
	}

	/**
	 * Get the x position which the block is moving to
	 * @return The x position which the block is moving to
	 */
	public int getMovingPosX()
	{
		return x;
	}

	/**
	 * Get the y position which the block is moving to
	 * @return The y position which the block is moving to
	 */
	public int getMovingPosY()
	{
		return y;
	}


	/**
	 * Set the position of the block
	 * @param x The new X position of the block
	 * @param y The new Y position of the block
	 */
	public void setPos(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * Initialize the block
     * @param environment Game environment interface
	 * @param c Boulderdash container interface
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
		this.redraw = true;
	}
	
	public Object clone()
		throws CloneNotSupportedException
	{
		Block b=(Block)super.clone();
		return b;
	}
	
	/**
	 * Check if the block needs to be redrawn
	 */
	public boolean needRedraw()
	{
		return redraw;
	}
	
	/**
	 * Set if the block should be redrawn or not
	 */
	protected void setRedraw(boolean redraw)
	{
		this.redraw = redraw;
	}
	/**
	 * Update the position and state of the block
	 */
	abstract public void update();
	
	/**
	 * Draw the block
	 * @param g Graphics object to draw on
	 */	
	abstract public void draw(Graphics g);


	/**
	 * Fill the block with black color
	 * @param g Graphics object to draw on
	 */	
	public void drawClear(Graphics g)
	{
		g.clearRect(cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),
			cont.getSquareSize(),cont.getSquareSize());

	}
}