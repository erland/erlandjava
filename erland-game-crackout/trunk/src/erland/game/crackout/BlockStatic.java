package erland.game.crackout;

import java.awt.*;

class BlockStatic extends Block
{
	BlockStatic()
	{
		description = "Static block";
	}
	
	public Object clone()
    	throws CloneNotSupportedException
    {
    	BlockStatic obj = new BlockStatic();
		obj.init(images, cont, sizeX, sizeY, posX, posY);
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