package erland.game.pipes;
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
import erland.util.*;

/**
 * Represents a pipe part
 */
abstract class PipePart
{
	/**
	 * Array which entries indicates which side of the pipe part that is open 
	 * and can receive/spill water.
	 * {@link Direction} is used as index in the array
	 */
	protected boolean openParts[];
	/** Indicates if the pipe part contains water */
	protected boolean waterInPart;
	/** Indicates which side of the pipe part water was received on, see {@link Direction} */
	protected int waterEntry;
	/** X position of pipe part (Part coordinate) */
	protected int x;
	/** Y position of pipe part (Part coordinate) */
	protected int y;
	/** Block container object which the part resides in */
	protected BlockContainerInterface cont;
	/** 
	 * Counter that indicates how far inside the part water has run, the part is full
	 * when waterProgress has been increased to the part width
	 */ 
	protected int waterProgress;
		
	/**
	 * Initialize pipe part
	 * @param cont Block container which the pipe part resides in
	 * @param x X position of pipe part (Part coordinate)
	 * @param y Y position of pipe part (Part coordinate)
	 */
	protected void init(BlockContainerInterface cont, int x, int y)
	{
		waterInPart=false;
		openParts = new boolean[4];
		for (int i=0; i<4; i++) {
			openParts[i]=false;
	    }
	    this.cont = cont;
	    this.x = x;
	    this.y = y;
	    Log.println(this,toString() + ":init: " + x + ", " + y);
	}

	/**
	 * Indicates if this block is as full with water as possible
	 * @return true/false (Full/Possible to add more water)
	 */
	public boolean isWaterFilled()
	{
		return (waterInPart && waterProgress==cont.getSquareSize());
	}
	/**
	 * Indicates if a specific side of the pipe part is open and can receive/spill water
	 * @param direction Side to check, see {@link Direction}
	 * @return true/false (Open/Not open)
	 */
	public boolean isOpen(int direction)
	{
		return openParts[direction];
	}
	
	/**
	 * Draw the pipe part
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(hasWater(Direction.LEFT) || hasWater(Direction.UP)|| hasWater(Direction.RIGHT) || hasWater(Direction.DOWN)) {
			drawBackground(g);
			g.setColor(Color.blue);
			drawWater(g);
			drawWaterForeground(g);
		}else {
			drawBackground(g);
		}
	}
	
	/**
	 * Add water to the pipe part on the specified side
	 * @param direction Side to add water on, see {@link Direction}
	 * @return <code>true</code> - Water successfully added
	 * <br><code>false</code> - Failed to add water to part on the specified side
	 */
	public boolean initWater(int direction)
	{
		Log.println(this,toString() + ":part.initWater : " + x + ", " + y + ", " + direction);
		if(isOpen(direction) && !hasWater(direction)) {
			Log.println(this,toString() + ":part.initWater2 : " + x + ", " + y + ", " + direction);
			waterEntry=direction;
			waterInPart=true;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Check if pipe part contains water in the specified direction, the direction
	 * does not matter for most pipe parts, but if the part contains several individual
	 * pipes like a cross pipe part it may matter. This method basically checks it it
	 * would be possible to add new water on the specified side
	 * @param direction Side to check, see {@link Direction}
	 * @return true/false (Contains water/Does not contain water)
	 */
	public boolean hasWater(int direction)
	{
		return waterInPart;
	}
	
	/**
	 * Get x position of pipe part
	 * @return x position of pipe part (Part coordinate)
	 */
	public int getXPosition()
	{
		Log.println(this,toString() + ":getXPosition : "+ x );
		return x;
	}
	/**
	 * Get x position of pipe part
	 * @return x position of pipe part (Part coordinate)
	 */
	public int getYPosition()
	{
		Log.println(this,toString() + ":getYPosition : "+ y );
		return y;
	}

	/**
	 * Check if the pipe parts wants to spill/leak water on the specified side
	 * @param direction Side to check
	 * @return true/false (Leak/Do not leak)
	 */
	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(waterProgress>=cont.getSquareSize()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Move water in pipe part one step further
	 * @return <code>true</code> - Water successfully moved
	 * <br><code>false</code> - Water moved, but part is now full and might want to leak water
	 * to adjacent parts
	 */
	public boolean moveWater()
	{
		Log.println(this,toString() + ": moveWater" + x + ", "+ y);
		if(waterInPart && waterProgress<cont.getSquareSize()) {
			waterProgress++;
			Log.println(this,toString() + ": " + waterProgress);
			if(waterProgress>=cont.getSquareSize()) {
				return false;
			}
		}else if(waterInPart) {
			return false;
		}
		return true;
	}
	/**
	 * Draw the water in the pipe part, this will be called after {@link #drawBackground} but
	 * before {@link #drawWaterForeground} if the pipe part contains water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawWater(Graphics g);

	/**
	 * Draw background of the pipe part, this is the only draw method that is called if the
	 * pipe part does not contain any water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawBackground(Graphics g);
	/**
	 * Draw the water foreground in the pipe part, this will be called after {@link #drawWater} and
	 * {@link #drawBackground} when the pipe part contains water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawWaterForeground(Graphics g);
}
