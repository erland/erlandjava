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
class BlockEarth extends Block
{
	/** Indicates how far the digging is progressing, 0 indicates that digging has not started yet */
	protected float digProgress;
	/** Indicates in which direction the digging is done */
	protected int digDirection;
	/** Indicates if the block is in progress to be digged through */
	protected boolean digging;
	
	/** The speed the digging id done with */
	protected float digSpeed;
	/** Image of the block */
	protected Image img;
	
	public void init(GameEnvironmentInterface environment, BoulderDashContainerInterface c, BlockContainerInterface cont, int x, int y)
	{
		super.init(environment,c,cont,x,y);
		img = environment.getImageHandler().getImage("earth.gif");
		digging = false;
	}

	public void update()
	{
		setRedraw(false);
		if(digging) {
			setRedraw(true);
			digProgress+=digSpeed;
			if(digProgress>=cont.getSquareSize()) {
				c.delBlock(this);
			}
		}
	}
	
	public void draw(Graphics g)
	{
		int dx = cont.getDrawingPositionX(x);
		int dy = cont.getDrawingPositionY(y);
		int width = cont.getSquareSize();
		int height = cont.getSquareSize();
		if(digging) {
			switch(digDirection) {
				case Direction.LEFT:
					dx+=cont.getSquareSize()-digProgress;
					width=(int)digProgress;
					break;
				case Direction.RIGHT:
					width=(int)digProgress;
					break;
				case Direction.UP:
					dy+=cont.getSquareSize()-digProgress;
					height=(int)digProgress;
					break;
				case Direction.DOWN:
					height=(int)digProgress;
					break;
				default:
					break;
			}
		}
		g.drawImage(img,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		if(digging) {
			g.setColor(Color.black);
			g.fillRect(dx,dy,width,height);
		}
	}

	public boolean isDigThrough()
	{
		return true;
	}
	
	public boolean dig(int direction, float speed)
	{
		if(!digging) {
			digging = true;
			digSpeed = speed;
			digProgress = 0;
			digDirection = direction;
			return true;
		}
		return false;
	}
}