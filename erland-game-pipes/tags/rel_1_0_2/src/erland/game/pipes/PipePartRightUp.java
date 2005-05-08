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

/**
 * Implements a pipe part with openings right and up
 */
class PipePartRightUp extends PipePart
{
	/** Background image */
	protected Image baseImage;
	/** Water foreground image */
	protected Image waterImage;
	/**
	 * Creates new pipe part
	 * @param environment Game environment object
	 */
	public PipePartRightUp(GameEnvironmentInterface environment)
	{
		baseImage = environment.getImageHandler().getImage("rightUpPipe.gif");
		waterImage = environment.getImageHandler().getImage("rightUpPipeWater.gif");
	}	
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,
			x,y);
		openParts[Direction.RIGHT] = true;
		openParts[Direction.UP] = true;
		waterProgress=0;
	}
	public void drawWater(Graphics g)
	{
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		if(waterEntry==Direction.RIGHT) {
			g.fillRect(x1+cont.getSquareSize()-waterProgress,y1,waterProgress,cont.getSquareSize());
		}else if(waterEntry==Direction.UP) {
			g.fillRect(x1,y1,cont.getSquareSize(),waterProgress);
		}
	}
	protected void drawWaterForeground(Graphics g)
	{
		g.drawImage(waterImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
	}
	protected void drawBackground(Graphics g)
	{
		g.drawImage(baseImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
	}
}
