package erland.game.pipes;
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
 