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
import erland.util.*;
import erland.game.*;

/**
 * Implements a pipe part with openings on all sides.
 * This part is thought to be a pool which can be filled with water
 */
class PipePartPool extends PipePart
{
	/** Background image */
	protected Image baseImage;
	/** Water foreground image */
	protected Image waterImage;
	/** Indicates number of levels in the pool that has been filled with water */
	int levelsFilled;
	/** Indicates number of levels in the poll that must be filled with water */
	final static int HEIGHT=5;

	/**
	 * Creates new pipe part
	 * @param environment Game environment object
	 */
	public PipePartPool(GameEnvironmentInterface environment)
	{
		baseImage = environment.getImageHandler().getImage("poolPipe.gif");
		waterImage = environment.getImageHandler().getImage("poolPipeWater.gif");
	}	
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,
			x,y);
		openParts[Direction.LEFT] = true;
		openParts[Direction.RIGHT] = true;
		openParts[Direction.UP] = true;
		openParts[Direction.DOWN] = true;
		waterProgress=0;
		levelsFilled=0;
	}
	public void drawWater(Graphics g)
	{
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		g.fillRect(x1+cont.getSquareSize()/2-levelsFilled,y1+cont.getSquareSize()/2-levelsFilled,levelsFilled*2,levelsFilled*2);
	}
	protected void drawWaterForeground(Graphics g)
	{
		g.drawImage(waterImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
	}
	protected void drawBackground(Graphics g)
	{
		g.drawImage(baseImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
	}

	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(levelsFilled>=HEIGHT) {
				return true;
			}
		}
		return false;
	}

	public boolean moveWater()
	{
		if(waterInPart && waterProgress<cont.getSquareSize()) {
			waterProgress++;
			Log.println(this,toString() + ": " + waterProgress);
			if(waterProgress>=cont.getSquareSize()) {
				levelsFilled++;
				waterProgress=0;
				if(levelsFilled>=HEIGHT) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Get number of levels that has been filled in the pool
	 * @return Number of levels that are filled with water
	 */
	protected int getLevelsFilled()
	{
		return levelsFilled;
	}

	public boolean isWaterFilled()
	{
		return (levelsFilled>=HEIGHT);
	}
}
