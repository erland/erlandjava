package erland.game.pipes;
import java.awt.*;
import erland.game.*;
import erland.util.*;

/**
 * Represents a pipe part
 */
abstract class PipePart
{
	/**
	 * Array which entries indicates which side of the pipe part that is open 
	 * and can receive/spill water.
	 * {@link Direction} is used as index in the array
	 */
	protected boolean openParts[];
	/** Indicates if the pipe part contains water */
	protected boolean waterInPart;
	/** Indicates which side of the pipe part water was received on, see {@link Direction} */
	protected int waterEntry;
	/** X position of pipe part (Part coordinate) */
	protected int x;
	/** Y position of pipe part (Part coordinate) */
	protected int y;
	/** Block container object which the part resides in */
	protected BlockContainerInterface cont;
	/** 
	 * Counter that indicates how far inside the part water has run, the part is full
	 * when waterProgress has been increased to the part width
	 */ 
	protected int waterProgress;
		
	/**
	 * Initialize pipe part
	 * @param cont Block container which the pipe part resides in
	 * @param x X position of pipe part (Part coordinate)
	 * @param y Y position of pipe part (Part coordinate)
	 */
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

	/**
	 * Indicates if a specific side of the pipe part is open and can receive/spill water
	 * @param direction Side to check, see {@link Direction}
	 * @return true/false (Open/Not open)
	 */
	public boolean isOpen(int direction)
	{
		return openParts[direction];
	}
	
	/**
	 * Draw the pipe part
	 * @param g Graphics object to draw on
	 */
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
	
	/**
	 * Add water to the pipe part on the specified side
	 * @param direction Side to add water on, see {@link Direction}
	 * @return <code>true</code> - Water successfully added
	 * <br><code>false</code> - Failed to add water to part on the specified side
	 */
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
	
	/**
	 * Check if pipe part contains water in the specified direction, the direction
	 * does not matter for most pipe parts, but if the part contains several individual
	 * pipes like a cross pipe part it may matter. This method basically checks it it
	 * would be possible to add new water on the specified side
	 * @param direction Side to check, see {@link Direction}
	 * @return true/false (Contains water/Does not contain water)
	 */
	public boolean hasWater(int direction)
	{
		return waterInPart;
	}
	
	/**
	 * Get x position of pipe part
	 * @return x position of pipe part (Part coordinate)
	 */
	public int getXPosition()
	{
		//System.out.println(toString() + ":getXPosition : "+ x );
		return x;
	}
	/**
	 * Get x position of pipe part
	 * @return x position of pipe part (Part coordinate)
	 */
	public int getYPosition()
	{
		//System.out.println(toString() + ":getYPosition : "+ y );
		return y;
	}

	/**
	 * Check if the pipe parts wants to spill/leak water on the specified side
	 * @param direction Side to check
	 * @return true/false (Leak/Do not leak)
	 */
	public boolean leakWater(int direction)
	{
		if(isOpen(direction)) {
			if(waterProgress>=cont.getSquareSize()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Move water in pipe part one step further
	 * @return <code>true</code> - Water successfully moved
	 * <br><code>false</code> - Water moved, but part is now full and might want to leak water
	 * to adjacent parts
	 */
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
	/**
	 * Draw the water in the pipe part, this will be called after {@link #drawBackground} but
	 * before {@link #drawWaterForeground} if the pipe part contains water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawWater(Graphics g);

	/**
	 * Draw background of the pipe part, this is the only draw method that is called if the
	 * pipe part does not contain any water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawBackground(Graphics g);
	/**
	 * Draw the water foreground in the pipe part, this will be called after {@link #drawWater} and
	 * {@link #drawBackground} when the pipe part contains water
	 * @param g Graphics object to draw on
	 * @see #drawWaterForeground
	 * @see #drawBackground
	 */
	abstract protected void drawWaterForeground(Graphics g);
}
