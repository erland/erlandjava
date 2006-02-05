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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements a pipe part with two separate pipes one from left to right
 * and one from up to down
 */
class PipePartCross extends PipePart
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(PipePartCross.class);
	/** Background image of part */
	protected Image baseImage;
	/** Water forground image when only up/down pipe contains water */
	protected Image waterImageUpDown;
	/** Water forground image when only left/right pipe contains water */
	protected Image waterImageLeftRight;
	/** Water forground image when both pipes contains water */
	protected Image waterImageCrossAll;
	/** Indicates which side of the up/down pipe water has been received on, see {@link Direction}  */
	protected int waterEntryUpDown;
	/** Indicates which side of the left/right pipe water has been received on, see {@link Direction} */
	protected int waterEntryLeftRight;
	/** Indicates if there is water in the up/down pipe */
	protected boolean waterInPartUpDown;
	/** Indicates if there is water in the left/right pipe */
	protected boolean waterInPartLeftRight;
	/** Indicates how far water has run in the up/down pipe */
	protected int waterProgressUpDown;
	/** Indicates how far water has run in the left/right pipe */
	protected int waterProgressLeftRight;

	/**
	 * Creates a new pipe part
	 * @param environment Game environment object
	 */
	public PipePartCross(GameEnvironmentInterface environment)
	{
		baseImage=environment.getImageHandler().getImage("crossPipe.gif");
		waterImageUpDown = environment.getImageHandler().getImage("crossUpDownPipeWater.gif");
		waterImageLeftRight = environment.getImageHandler().getImage("crossLeftRightPipeWater.gif");
		waterImageCrossAll = environment.getImageHandler().getImage("crossAllPipeWater.gif");
	}	
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,x,y);
		openParts[Direction.LEFT] = true;
		openParts[Direction.RIGHT] = true;
		openParts[Direction.UP] = true;
		openParts[Direction.DOWN] = true;
		waterProgress=0;
		waterProgressLeftRight=0;
		waterProgressUpDown=0;
	}
	public void drawWater(Graphics g)
	{
		final int OUTSIDE_PIPE = 2;
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		if(waterInPartLeftRight && waterEntryLeftRight==Direction.LEFT) {
			g.fillRect(x1,y1+OUTSIDE_PIPE,waterProgressLeftRight,cont.getSquareSize()-OUTSIDE_PIPE);
		}else if(waterInPartLeftRight && waterEntryLeftRight==Direction.RIGHT) {
			g.fillRect(x1+cont.getSquareSize()-waterProgressLeftRight,y1+OUTSIDE_PIPE,waterProgressLeftRight,cont.getSquareSize()-OUTSIDE_PIPE);
		}
		if(waterInPartUpDown && waterEntryUpDown==Direction.UP) {
			if(waterProgressUpDown<=OUTSIDE_PIPE) {
				g.fillRect(x1,y1,cont.getSquareSize(),waterProgressUpDown);
			}else {
				g.fillRect(x1,y1,cont.getSquareSize(),OUTSIDE_PIPE);
			}
			if(waterProgressUpDown>(cont.getSquareSize()-OUTSIDE_PIPE)) {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),OUTSIDE_PIPE-(cont.getSquareSize()-waterProgressUpDown));
			}
		}else if(waterInPartUpDown && waterEntryUpDown==Direction.DOWN) {
			if(waterProgressUpDown<=OUTSIDE_PIPE) {
				g.fillRect(x1,y1+cont.getSquareSize()-waterProgressUpDown,cont.getSquareSize(),waterProgressUpDown);
			}else {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),2);
			}
			if(waterProgressUpDown>(cont.getSquareSize()-OUTSIDE_PIPE)) {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),2);
				g.fillRect(x1,y1+cont.getSquareSize()-waterProgressUpDown,cont.getSquareSize(),OUTSIDE_PIPE-(cont.getSquareSize()-waterProgressUpDown));
			}			
		}

	}
	protected void drawBackground(Graphics g)
	{
		g.drawImage(baseImage,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
	}
	protected void drawWaterForeground(Graphics g)
	{
		if(waterInPartLeftRight && waterInPartUpDown) {
			g.drawImage(waterImageCrossAll,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}else if(waterInPartLeftRight) {
			g.drawImage(waterImageLeftRight,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}else {
			g.drawImage(waterImageUpDown,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}
	}
	public boolean initWater(int direction)
	{
		LOG.debug(toString() + ":initWater : " + direction);
		if(isOpen(direction) && !hasWater(direction)) {
			if(direction==Direction.LEFT || direction==Direction.RIGHT) {
				LOG.debug(toString() + ":initWater2: " + direction);
				waterEntryLeftRight = direction;
				waterInPartLeftRight = true;
				waterInPart = true;
			}else {
				waterEntryUpDown = direction;
				waterInPartUpDown = true;
				waterInPart = true;
			}
			return true;
		}else {
			return false;
		}
	}

	public boolean hasWater(int direction)
	{
		LOG.debug(toString() + ":hasWater : " + direction);
		if(direction==Direction.LEFT || direction==Direction.RIGHT) {
			LOG.debug(toString() + ":hasWater : " + direction + ", " + waterInPartLeftRight);
			return waterInPartLeftRight;
		}else {
			return waterInPartUpDown;
		}
	}
	
	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(direction==Direction.LEFT || direction==Direction.RIGHT) {
				if(waterInPartLeftRight && waterProgressLeftRight>=cont.getSquareSize()) {
					return true;
				}
			}else if(direction==Direction.UP || direction==Direction.DOWN) {
				if(waterInPartUpDown && waterProgressUpDown>=cont.getSquareSize()) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean moveWater()
	{
		LOG.debug(toString() + ":moveWater : " + waterInPartLeftRight + ", " + waterInPartUpDown);
		if(waterInPartLeftRight && waterProgressLeftRight<cont.getSquareSize()) {
			waterProgressLeftRight++;
			if(waterProgressLeftRight>=cont.getSquareSize()) {
				return false;
			}
		}
		if(waterInPartUpDown && waterProgressUpDown<cont.getSquareSize()) {
			waterProgressUpDown++;
			if(waterProgressUpDown>=cont.getSquareSize()) {
				return false;
			}
		}
		return true;
	}
	public boolean isWaterFilled()
	{
		return (waterInPartLeftRight && waterProgressLeftRight==cont.getSquareSize() && waterInPartUpDown && waterProgressUpDown==cont.getSquareSize());
	}
}
