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
 * Creates a block that never disappears when it is hit by the ball
 * It will however disappear if it is hit by an explosion
 */
class BlockStatic extends Block
{
	/**
	 * Creates a new block
	 */
	public BlockStatic()
	{
		description = "Static block";
	}
	
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockStatic obj = new BlockStatic();
		obj.init(environment, cont, sizeX, sizeY, posX, posY);
		return obj;
    }

	public int getScore()
	{
		return 0;
	}
	public boolean isRemovable()
	{
		return false;	
	}
	public void handleCollision(ActionInterface a)
	{
		// Do nothing, this should always be active;
	}

	public void handleCollisionExplosive(ActionInterface a)
	{
		active=false;
	}

	public void draw(Graphics g)
	{
		if(active) {
			int offsetX = cont.getOffsetX();
			int offsetY = cont.getOffsetY();
			int squareSize = cont.getSquareSize();
			g.setColor(new Color(0x888888));
			g.fillRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
			g.setColor(new Color(0x666666));
			g.drawRect(offsetX+1+posX*squareSize, offsetY+1+posY*squareSize, squareSize*sizeX-3, squareSize*sizeY-3);
			g.setColor(new Color(0x444444));
			g.drawRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
		}
	}
	public void setColor(Color color)
	{
		// Do nothing, it should always be gray
	}
	public Color getColor()
	{
		return Color.gray;
	}
}