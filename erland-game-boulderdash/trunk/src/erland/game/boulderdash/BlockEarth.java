package erland.game.boulderdash;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Represents a block on the game area
 */
class BlockEarth extends Block
{
	/** Indicates how far the digging is progressing, 0 indicates that digging has not started yet */
	protected int digProgress;
	/** Indicates in which direction the digging is done */
	protected int digDirection;
	/** Indicates if the block is in progress to be digged through */
	protected boolean digging;
	
	/** Image of the block */
	protected Image img;
	
	public void init(BoulderDashContainerInterface c, ImageHandlerInterface images, BlockContainerInterface cont, int x, int y)
	{
		super.init(c,images,cont,x,y);
		img = images.getImage("earth.gif");
		digging = false;
	}

	public void update()
	{
		if(digging) {
			digProgress++;
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
					width=digProgress;
					break;
				case Direction.RIGHT:
					width=digProgress;
					break;
				case Direction.UP:
					dy+=cont.getSquareSize()-digProgress;
					height=digProgress;
					break;
				case Direction.DOWN:
					height=digProgress;
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
	
	public boolean dig(int direction)
	{
		if(!digging) {
			digging = true;
			digProgress = 0;
			digDirection = direction;
			return true;
		}
		return false;
	}
}