package erland.game.pipes;
import java.awt.*;
import erland.util.*;
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
	 * @param images Image handler object
	 */
	public PipePartNone(ImageHandlerInterface images)
	{
		baseImage = images.getImage("noPipe.gif");
	}
	/**
	 * Creates new pipe part with the specified background image
	 * @param images Image handler object
	 * @param image Image to have as the background
	 */
	public PipePartNone(ImageHandlerInterface images, String image)
	{
		baseImage = images.getImage(image);
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
 