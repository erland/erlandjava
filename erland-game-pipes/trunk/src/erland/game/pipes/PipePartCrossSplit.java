package erland.game.pipes;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Implements a pipe part with openings on all sides
 */
class PipePartCrossSplit extends PipePart
{
	protected Image baseImage;
	protected Image waterImage;
	public PipePartCrossSplit(ImageHandlerInterface images)
	{
		baseImage = images.getImage("crossSplitPipe.gif");
		waterImage = images.getImage("crossSplitPipeWater.gif");

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
	}
	public void drawWater(Graphics g)
	{
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		if(waterEntry==Direction.LEFT) {
			g.fillRect(x1,y1,waterProgress,cont.getSquareSize());
		}else if(waterEntry==Direction.RIGHT) {
			g.fillRect(x1+cont.getSquareSize()-waterProgress,y1,waterProgress,cont.getSquareSize());
		}if(waterEntry==Direction.UP) {
			g.fillRect(x1,y1,cont.getSquareSize(),waterProgress);
		}else if(waterEntry==Direction.DOWN) {
			g.fillRect(x1,y1+cont.getSquareSize()-waterProgress,cont.getSquareSize(),waterProgress);
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
