package erland.game.pipes;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Implements a pipe part with openings left and down
 */
class PipePartCross extends PipePart
{
	protected Image baseImage;
	protected Image waterImageUpDown;
	protected Image waterImageLeftRight;
	protected Image waterImageCrossAll;
	protected int waterEntryUpDown;
	protected int waterEntryLeftRight;
	protected boolean waterInPartUpDown;
	protected boolean waterInPartLeftRight;
	protected int waterProgressUpDown;
	protected int waterProgressLeftRight;

	public PipePartCross(ImageHandlerInterface images)
	{
		baseImage=images.getImage("crossPipe.gif");
		waterImageUpDown = images.getImage("crossUpDownPipeWater.gif");
		waterImageLeftRight = images.getImage("crossLeftRightPipeWater.gif");
		waterImageCrossAll = images.getImage("crossAllPipeWater.gif");
	}	
	public void init(BlockContainerInterface cont, int x, int y)
	{
		super.init(cont,x,y);
		openParts[Direction.LEFT] = true;
		openParts[Direction.RIGHT] = true;
		openParts[Direction.UP] = true;
		openParts[Direction.DOWN] = true;
		waterProgress=0;
		waterProgressLeftRight=0;
		waterProgressUpDown=0;
	}
	public void drawWater(Graphics g)
	{
		final int OUTSIDE_PIPE = 2;
		int x1 = cont.getDrawingPositionX(x);
		int y1 = cont.getDrawingPositionY(y);
		if(waterInPartLeftRight && waterEntryLeftRight==Direction.LEFT) {
			g.fillRect(x1,y1+OUTSIDE_PIPE,waterProgressLeftRight,cont.getSquareSize()-OUTSIDE_PIPE);
		}else if(waterInPartLeftRight && waterEntryLeftRight==Direction.RIGHT) {
			g.fillRect(x1+cont.getSquareSize()-waterProgressLeftRight,y1+OUTSIDE_PIPE,waterProgressLeftRight,cont.getSquareSize()-OUTSIDE_PIPE);
		}
		if(waterInPartUpDown && waterEntryUpDown==Direction.UP) {
			if(waterProgressUpDown<=OUTSIDE_PIPE) {
				g.fillRect(x1,y1,cont.getSquareSize(),waterProgressUpDown);
			}else {
				g.fillRect(x1,y1,cont.getSquareSize(),OUTSIDE_PIPE);
			}
			if(waterProgressUpDown>(cont.getSquareSize()-OUTSIDE_PIPE)) {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),OUTSIDE_PIPE-(cont.getSquareSize()-waterProgressUpDown));
			}
		}else if(waterInPartUpDown && waterEntryUpDown==Direction.DOWN) {
			if(waterProgressUpDown<=OUTSIDE_PIPE) {
				g.fillRect(x1,y1+cont.getSquareSize()-waterProgressUpDown,cont.getSquareSize(),waterProgressUpDown);
			}else {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),2);
			}
			if(waterProgressUpDown>(cont.getSquareSize()-OUTSIDE_PIPE)) {
				g.fillRect(x1,y1+cont.getSquareSize()-OUTSIDE_PIPE,cont.getSquareSize(),2);
				g.fillRect(x1,y1+cont.getSquareSize()-waterProgressUpDown,cont.getSquareSize(),OUTSIDE_PIPE-(cont.getSquareSize()-waterProgressUpDown));
			}			
		}

	}
	protected void drawBackground(Graphics g)
	{
		g.drawImage(baseImage,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
	}
	protected void drawWaterForeground(Graphics g)
	{
		if(waterInPartLeftRight && waterInPartUpDown) {
			g.drawImage(waterImageCrossAll,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}else if(waterInPartLeftRight) {
			g.drawImage(waterImageLeftRight,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}else {
			g.drawImage(waterImageUpDown,cont.getDrawingPositionX(x),cont.getDrawingPositionY(y),null);
		}
	}
	public boolean initWater(int direction)
	{
		//System.out.println(toString() + ":initWater : " + direction);
		if(isOpen(direction)) {
			if(direction==Direction.LEFT || direction==Direction.RIGHT) {
				//System.out.println(toString() + ":initWater2: " + direction);
				waterEntryLeftRight = direction;
				waterInPartLeftRight = true;
				waterInPart = true;
			}else {
				waterEntryUpDown = direction;
				waterInPartUpDown = true;
				waterInPart = true;
			}
			return true;
		}else {
			return false;
		}
	}
	public boolean hasWater(int direction)
	{
		//System.out.println(toString() + ":hasWater : " + direction);
		if(direction==Direction.LEFT || direction==Direction.RIGHT) {
			//System.out.println(toString() + ":hasWater : " + direction + ", " + waterInPartLeftRight);
			return waterInPartLeftRight;
		}else {
			return waterInPartUpDown;
		}
	}
	
	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(direction==Direction.LEFT || direction==Direction.RIGHT) {
				if(waterInPartLeftRight && waterProgressLeftRight>=cont.getSquareSize()) {
					return true;
				}
			}else if(direction==Direction.UP || direction==Direction.DOWN) {
				if(waterInPartUpDown && waterProgressUpDown>=cont.getSquareSize()) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean moveWater()
	{
		//System.out.println(toString() + ":moveWater : " + waterInPartLeftRight + ", " + waterInPartUpDown);
		if(waterInPartLeftRight && waterProgressLeftRight<cont.getSquareSize()) {
			waterProgressLeftRight++;
			if(waterProgressLeftRight>=cont.getSquareSize()) {
				return false;
			}
		}
		if(waterInPartUpDown && waterProgressUpDown<cont.getSquareSize()) {
			waterProgressUpDown++;
			if(waterProgressUpDown>=cont.getSquareSize()) {
				return false;
			}
		}
		return true;
	}
}
