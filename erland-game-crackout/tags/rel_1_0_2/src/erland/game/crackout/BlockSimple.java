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
import erland.game.*;
import java.awt.*;

/**
 * Implements a simple block that will disappear as soon
 * has it has been hit
 */
class BlockSimple extends Block
{
	/** Counter that handles the fadeout when the block has been hit */
	protected int dieCount;
	/** Color of the block */
	protected Color color;
	
	/**
	 * Creates a new instance of the block
	 */
	public BlockSimple()
	{
		dieCount=255;
		color = Color.blue;
		this.description = "Simple block";
	}
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockSimple obj = new BlockSimple();
		obj.init(environment, cont, sizeX, sizeY, posX, posY, color);
		return obj;
    }
	/**
	 * Initialize block
	 * @param environment Game environment object
	 * @param cont Reference to block container object
	 * @param sizeX Width of block (Number of squares)
	 * @param sizeY Height of block (Number of squares)
	 * @param posX X position of block (Square coordinates)
	 * @param posY Y position of block (Square coordinates)
	 * @param color Color of the block
	 */
	public void init(GameEnvironmentInterface environment, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY, Color color)
	{
		init(environment, cont , sizeX, sizeY, posX, posY);
		this.color = color;
	}
	public int getScore()
	{
		return 10;
	}
	public boolean isRemovable()
	{
		return true;	
	}
	/**
	 * Draws a simple block
	 * @param g Graphics object to draw on
	 * @param color The color that the block should be drawn in
	 * @param brightness The brightness the block should be drawn in. 0 is completely black, 256 is normal and 511 is completely white
	 */
	protected void drawBlock(Graphics g, Color color, int brightness)
	{
		float hsb[] = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),hsb);

		if(brightness>255) {
			brightness-=256;
			hsb[1]=(float)((double)(256-brightness)/255.0);
		}else {
			hsb[2]=(float)((double)brightness/255.0);
		}
		Color c = Color.getHSBColor(hsb[0],hsb[1],hsb[2]);
		g.setColor(c);
		Color.RGBtoHSB(c.getRed(), c.getGreen(),c.getBlue(),hsb);
		float darkness = hsb[2];
		int offsetX = cont.getOffsetX();
		int offsetY = cont.getOffsetY();
		int squareSize = cont.getSquareSize();
		g.fillRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
		hsb[2]=darkness/3;
		g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		g.drawRect(offsetX+posX*squareSize, offsetY+posY*squareSize, squareSize*sizeX-1, squareSize*sizeY-1);
		
		hsb[2]=darkness*2/3;
		g.setColor(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
		g.drawRect(offsetX+1+posX*squareSize, offsetY+1+posY*squareSize, squareSize*sizeX-3, squareSize*sizeY-3);
	}
	public void draw(Graphics g)
	{
		if(active) {
			drawBlock(g,color,0xFF);
		}else {
			if(dieCount>10) {
				dieCount-=10;
				drawBlock(g,color,dieCount);
			}
		}
	}
	public void setColor(Color color)
	{
		this.color = color;
	}
	public Color getColor()
	{
		return this.color;
	}
}