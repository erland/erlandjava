package erland.game.boulderdash;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Represents a block on the game area
 */
 
abstract class Block
{
	/** Image handler object */
	protected ImageHandlerInterface images;
	
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object */
	protected BlockContainerInterface cont;
	
	/** X position of the block */
	protected int x;

	/** Y position of the block */
	protected int y;

	/**
	 * Checks if it is possible to dig through the block
	 * @return true/false (Possible to dig through/Not possible to dig through)
	 */
	public boolean isDigThrough()
	{
		return false;
	}
	
	/**
	 * Dig through block in specified direction
	 * @param direction The direction in which the digging is done, see {@link erland.game#Direction}
	 * @return true/false (Success/Failure)
	 */
	public boolean dig(int direction)
	{
		return false;
	}

	/**
	 * Checks if the block is slippery so blocks on top of it also slippery will slide
	 * @return true/false (Slippery/Not slippery)
	 */
	public boolean isSlippery()
	{
		return false;
	}

	/**
	 * Checks if this block is movable
	 * @return true/false (Movable/Not movable)
	 */
	public boolean isMovable()
	{
		return false;
	}
	
	/**
	 * Move the block one step in the specified direction
	 * @param direction Direction to move the block in
	 * @return true/false (Success/Failure)
	 */
	public boolean move(int direction)
	{
		return false;
	}

	/**
	 * Indicates if the block is currently moving
	 * @return true/false (Moving/Not moving)
	 */
	public boolean isMoving()
	{
		return false;
	}

	
	/**
	 * Get the x position of the block
	 * @return The x position of the block
	 */
	public int getPosX()
	{
		return x;
	}

	/**
	 * Get the y position of the block
	 * @return The y position of the block
	 */
	public int getPosY()
	{
		return y;
	}

	/**
	 * Get the x position which the block is moving to
	 * @return The x position which the block is moving to
	 */
	public int getMovingPosX()
	{
		return x;
	}

	/**
	 * Get the y position which the block is moving to
	 * @return The y position which the block is moving to
	 */
	public int getMovingPosY()
	{
		return y;
	}


	/**
	 * Set the position of the block
	 * @param x The new X position of the block
	 * @param y The new Y position of the block
	 */
	public void setPos(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * Initialize the block
	 * @param c Boulderdash container interface
	 * @param images Image handler interface
	 * @param cont Block container interface
	 * @param x X position of the block
	 * @param y Y position of the block
	 */
	public void init(BoulderDashContainerInterface c, ImageHandlerInterface images, BlockContainerInterface cont, int x, int y)
	{
		this.c = c;
		this.images = images;
		this.cont = cont;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Update the position and state of the block
	 */
	abstract public void update();
	
	/**
	 * Draw the block
	 * @param g Graphics object to draw on
	 */	
	abstract public void draw(Graphics g);
}