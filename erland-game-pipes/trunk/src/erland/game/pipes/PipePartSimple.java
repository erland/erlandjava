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
 * Implements a pipe part this is configurable
 */
class PipePartSimple extends PipePart
{
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(PipePartSimple.class);
	/** Background image */
	protected Image baseImage;
	/** Water foreground image */
	protected Image waterImage;
	/** Open on left side */
	protected boolean openLeft;
	/** Open on right side */
	protected boolean openRight;
	/** Open on up side */
	protected boolean openUp;
	/** Open on down side */
	protected boolean openDown;
	/** Indicates if the pipe should be waterfilled from the start */
	protected boolean waterFilled;
	
	/**
	 * Creates new pipe part
	 * @param environment Game environment object
	 */
	public PipePartSimple(GameEnvironmentInterface environment, String baseBitmap, String waterBitmap, boolean waterFilled, boolean openLeft, boolean openRight, boolean openUp, boolean openDown)
	{
		if(baseBitmap!=null && baseBitmap.length()>0) {
			baseImage = environment.getImageHandler().getImage(baseBitmap);
		}else {
			baseImage = null;
		}
		if(waterBitmap!=null && waterBitmap.length()>0) {
			waterImage = environment.getImageHandler().getImage(waterBitmap);
			if(baseImage==null) {
				baseImage = waterImage;
			}
		}else {
			waterImage = null;
		}
		this.openLeft = openLeft;
		this.openRight = openRight;
		this.openUp = openUp;
		this.openDown = openDown;
		this.waterFilled = waterFilled;
	}	
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,
			x,y);
		LOG.debug(toString() + " :init:" + x+","+y+":"+openLeft + openRight + openUp + openDown);
		if(openLeft) {
			openParts[Direction.LEFT] = true;
		}
		if(openRight) {
			openParts[Direction.RIGHT] = true;
		}
		if(openUp) {
			openParts[Direction.UP] = true;
		}
		if(openDown) {
			openParts[Direction.DOWN] = true;
		}
	}
	public void drawWater(Graphics g)
	{
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		if(waterEntry==Direction.LEFT) {
			g.fillRect(x1,y1,waterProgress,cont.getSquareSize());
		}else if(waterEntry==Direction.RIGHT) {
			g.fillRect(x1+cont.getSquareSize()-waterProgress,y1,waterProgress,cont.getSquareSize());
		}else if(waterEntry==Direction.DOWN) {
			g.fillRect(x1,y1+cont.getSquareSize()-waterProgress,cont.getSquareSize(),waterProgress);
		}else if(waterEntry==Direction.UP) {
			g.fillRect(x1,y1,cont.getSquareSize(),waterProgress);
		}
	}
	protected void drawWaterForeground(Graphics g)
	{
		if(waterImage!=null) {
			g.drawImage(waterImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
		}
	}
	protected void drawBackground(Graphics g)
	{
		if(baseImage!=null) {
			g.drawImage(baseImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
		}
	}

	public boolean initWater(int direction)
	{
		if(!waterFilled) {
			return super.initWater(direction);
		}else {
			if(waterInPart==false) {
				waterProgress = cont.getSquareSize();
				waterEntry=direction;
				waterInPart=true;
				if(isOpen(direction)) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}
}
