package erland.game.pipes;
import java.awt.*;
import erland.game.*;

/**
 * Implements a pipe part with openings on all sides.
 * This part is thought to be a well which has water from
 * the beginning
 */
class PipePartWell extends PipePart
{
	/** Background image */
	protected Image baseImage;
	/** Water foreground image */
	protected Image waterImage;
	/** Indicates if the well is filled with water from the beginning */
	protected boolean waterFilled;
	/**
	 * Creates new pipe part
	 * @param environment Game environment object
	 */
	public PipePartWell(GameEnvironmentInterface environment, boolean waterFilled)
	{
		baseImage = environment.getImageHandler().getImage("wellPipe.gif");
		waterImage = environment.getImageHandler().getImage("wellPipeWater.gif");
		this.waterFilled = waterFilled;
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
		}else if(waterEntry==Direction.UP) {
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

	public boolean initWater(int direction)
	{
		if(!waterFilled) {
			return super.initWater(direction);
		}else {
			if(waterInPart==false) {
				waterProgress = cont.getSquareSize();
				waterEntry=direction;
				waterInPart=true;
				return true;
			}
		}
		return false;
	}
}
