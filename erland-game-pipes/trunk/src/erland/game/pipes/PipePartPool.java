package erland.game.pipes;
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

	/**
	 * Creates new pipe part
	 * @param images Image handler object
	 */
	public PipePartPool(ImageHandlerInterface images)
	{
		baseImage = images.getImage("poolPipe.gif");
		waterImage = images.getImage("poolPipeWater.gif");
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
			if(levelsFilled>4) {
				return true;
			}
		}
		return false;
	}

	public boolean moveWater()
	{
		if(waterInPart && waterProgress<cont.getSquareSize()) {
			waterProgress++;
			//System.out.println(toString() + ": " + waterProgress);
			if(waterProgress>=cont.getSquareSize()) {
				levelsFilled++;
				waterProgress=0;
				if(levelsFilled>4) {
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
}
