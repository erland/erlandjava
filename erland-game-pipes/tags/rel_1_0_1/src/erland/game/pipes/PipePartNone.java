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
 * Implements pipe part with no pipe
 */
class PipePartNone extends PipePart
{
	/** Background image */
	protected Image baseImage;
	/**
	 * Creates new pipe part
	 * @param environment Game environment object
	 */
	public PipePartNone(GameEnvironmentInterface environment)
	{
		baseImage = environment.getImageHandler().getImage("noPipe.gif");
	}
	/**
	 * Creates new pipe part with the specified background image
	 * @param environment Game environment object
	 * @param image Image to have as the background
	 */
	public PipePartNone(GameEnvironmentInterface environment, String image)
	{
		baseImage = environment.getImageHandler().getImage(image);
	}
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,
			x,y);
	}
	public void drawWater(Graphics g)
	{
		return;
	}
	public boolean fullWithWater()
	{
		return false;
	}
	public boolean moveWater()
	{
		return false;
	}
	protected void drawWaterForeground(Graphics g)
	{
		// No water can be in this pipe part
	}
	protected void drawBackground(Graphics g)
	{
		g.drawImage(baseImage,cont.getDrawingPositionX(x), cont.getDrawingPositionY(y),null);
	}
	public boolean isWaterFilled()
	{
		return true;
	}
}
 