package erland.game.crackout;
import erland.game.*;
import java.awt.*;

/**
 * This is the base class that all blocks are
 * derived from
 */
abstract class Block
	implements CollisionRect
{
	/** Width of the block (Number of squares) */
	protected int sizeX;
	/** Height of the block (Number of squares) */
	protected int sizeY;
	/** X position of block (Square coordinates) */
	protected int posX;
	/** Y position of block (Square coordinates) */
	protected int posY;
	/** Indicates if the block is visible(active) or not */
	protected boolean active;
	/** Textual description of the block */
	protected String description;
	/** Image handler object */
	protected ImageHandlerInterface images;
	/** Reference to block container object */
	protected BlockContainerInterface cont;
	
	/**
	 * Duplicate/clone the block
	 */
	abstract public Object clone()
    	throws CloneNotSupportedException;

	/**
	 * Initialize block
	 * @param images Image handler object
	 * @param cont Reference to block container object
	 * @param sizeX Width of block (Number of squares)
	 * @param sizeY Height of block (Number of squares)
	 * @param posX X position of block (Square coordinates)
	 * @param posY Y position of block (Square coordinates)
	 */
	public void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = posX;
		this.posY = posY;
		this.images = images;
		this.cont = cont;
		active = true;
	} 
	
	/**
	 * Set the block container this block resides in
	 * @cont Reference to block container object
	 */
	public void setContainer(BlockContainerInterface cont)
	{
		this.cont = cont;
	}	
	
	/**
	 * Get the current block container
	 * @return Current block container
	 */
	public BlockContainerInterface getContainer()
	{
		return cont;
	}
	
	/**
	 * Set position of block
	 * @param x X position of block (Square coordinates)
	 * @param y Y position of block (Square coordinates)
	 */
	public void setPosition(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * Get x position of block
	 * @return X position of block (Square coordinates)
	 */
	public int getPositionX()
	{
		return posX;
	}
	
	/**
	 * Get y position of block
	 * @return Y position of block (Square coordinates)
	 */
	public int getPositionY()
	{
		return posY;
	}
	
	
	public int left()
	{
		if(active) {
			return posX*cont.getSquareSize();
		}else {
			return -1;
		}
	}
	
	public int right()
	{
		if(active) {
			return posX*cont.getSquareSize()+cont.getSquareSize()*sizeX;
		}else {
			return -1;
		}
	}
	
	public int top()
	{
		if(active) {
			return posY*cont.getSquareSize();
		}else {
			return -1;
		}
	}
	
	public int bottom()
	{
		if(active) {
			return posY*cont.getSquareSize()+cont.getSquareSize()*sizeY;
		}else {
			return -1;
		}
	}

	/**
	 * Implements what will happen when the block is hit by the ball
	 * @param a Referens to an action object that implements the collision actions
	 */
	public void handleCollision(ActionInterface a)
	{
		active=false;
	}
	
	/**
	 * Implements what will happen when the block is hit during an explosion
	 * @param a Referens to an action object that implements the collision actions
	 */
	public void handleCollisionExplosive(ActionInterface a)
	{
		active=false;
	}
	
	/**
	 * Indicates if the block is visible(active) or not
	 * @return true/false (Active/Not active)
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * Get a description text of the block
	 * @return Description of block
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Set the color of the block
	 * @param color The new color
	 */
	public abstract void setColor(Color color);
	
	/**
	 * Get the current color of the block
	 * @return The current color
	 */
	public abstract Color getColor();
	
	/**
	 * Get the score that should be added when the block is hit
	 */
	public abstract int getScore();
	
	/**
	 * Indicates if this block can be hit so it disappears
	 * @return true/false (Disappears/Does not disappear)
	 */
	public abstract boolean isRemovable();
	
	/**
	 * Draws the block
	 * @param g Graphics object to draw on
	 */
	public abstract void draw(Graphics g);
}
