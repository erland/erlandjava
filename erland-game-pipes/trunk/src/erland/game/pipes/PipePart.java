package erland.game.pipes;
import java.awt.*;
import erland.game.*;
import erland.util.*;

/**
 * Represents a pipe part
 */
abstract class PipePart
{
	abstract class Direction
	{
		public static final int LEFT=0;
		public static final int RIGHT=1;
		public static final int UP=2;
		public static final int DOWN=3;
	}
	protected boolean openParts[];
	protected boolean waterInPart;
	protected int waterEntry;
	protected int x;
	protected int y;
	protected BlockContainerInterface cont;
	protected int waterProgress;
			
	protected void init(BlockContainerInterface cont, int x, int y)
	{
		waterInPart=false;
		openParts = new boolean[4];
		for (int i=0; i<4; i++) {
			openParts[i]=false;
	    }
	    this.cont = cont;
	    this.x = x;
	    this.y = y;
	    //System.out.println(toString() + ":init: " + x + ", " + y);
	}

	public boolean isOpen(int direction)
	{
		return openParts[direction];
	}
	
	public void draw(Graphics g)
	{
		if(waterInPart) {
			drawBackground(g);
			g.setColor(Color.blue);
			drawWater(g);
			drawWaterForeground(g);
		}else {
			drawBackground(g);
		}
	}
	
	public boolean initWater(int direction)
	{
		//System.out.println(toString() + ":part.initWater : " + x + ", " + y);
		if(isOpen(direction)) {
			waterEntry=direction;
			waterInPart=true;
			return true;
		}else {
			return false;
		}
	}
	public boolean hasWater(int direction)
	{
		return waterInPart;
	}
	public int getXPosition()
	{
		//System.out.println(toString() + ":getXPosition : "+ x );
		return x;
	}
	public int getYPosition()
	{
		//System.out.println(toString() + ":getYPosition : "+ y );
		return y;
	}
/*	public boolean fullWithWater()
	{
		if(waterProgress>=cont.getSquareSize()) {
			return true;
		}else {
			return false;
		}
	}
*/	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(waterProgress>=cont.getSquareSize()) {
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
				return false;
			}
		}
		return true;
	}
	abstract protected void drawWater(Graphics g);
	abstract protected void drawBackground(Graphics g);
	abstract protected void drawWaterForeground(Graphics g);
}
