package erland.game.boulderdash;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Represents a block on the game area
 */
 
class Player
{
	/** Image handler object */
	protected ImageHandlerInterface images;
	
	/** Boulderdash container object */
	protected BoulderDashContainerInterface c;
	
	/** Block container object */
	protected BlockContainerInterface cont;
	
	/** X position of the player */
	protected int x;

	/** Y position of the player */
	protected int y;

	/** Indicates that the block is moving */
	protected boolean moving;
	
	/** Indicates the moving direction of the player, see {@link erland.game#Direction} */
	protected int movingDirection;

	/** Indicates the moving progress in the direction specified in {link #movingDirection} */
	protected int movingProgress;
	
	/** Indicates if the player is alive or not */
	protected boolean alive;
	
	/** Indicates the moving speed */
	protected int movingSpeed;
	
	/** Image for the player */
	protected Image img;
	
	/**
	 * Move the player one step in the specified direction
	 * @param direction Direction to move the player in
	 * @return true/false (Success/Failure)
	 */
	public boolean move(int direction)
	{
		boolean result=false;
		switch(direction) {
			case Direction.LEFT:
				result = moveBlock(x-1,y,direction);
				break;
			case Direction.RIGHT:
				result = moveBlock(x+1,y,direction);
				break;
			case Direction.UP:
				result = moveBlock(x,y-1,direction);
				break;
			case Direction.DOWN:
				result = moveBlock(x,y+1,direction);
				break;
			default:
				result = false;
				break;
		}
		if(result) {
			moving = true;
			movingProgress = 0;
			movingDirection = direction;
		}
		return result;
	}

	/**
	 * Move a block
	 * @param x The x position of the block to move
	 * @param y The y position of the block to move
	 * @param direction The direction to move the block in
	 * @return true/false (Success/Failure)
	 */
	protected boolean moveBlock(int x, int y, int direction)
	{
		boolean result = false;
		if(c.isFree(x,y)) {
			movingSpeed = 2;
			result = true;
		}else if(c.isMovable(x,y)) {
			if(c.moveBlock(x,y,direction)) {
				result = true;
				movingSpeed = 1;
			}
		}else if(c.isDigThrough(x,y)) {
			if(c.digBlock(x,y,direction)) {
				result = true;
				movingSpeed = 1;
			}
		}
		return result;
	}

	/**
	 * Indicates if the block is currently moving
	 * @return true/false (Moving/Not moving)
	 */
	public boolean isMoving()
	{
		return moving;
	}
	
	/**
	 * Gets the current moving direction of the player
	 * @return The moving direction, see {@link erland.game#Direction}
	 */
	public int movingDirection()
	{
		if(isMoving()) {
			return movingDirection;
		}
		return Direction.UP;
	}
	
	/**
	 * Initialize player object
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
		this.alive = true;
		this.moving = false;
		this.img = images.getImage("player.gif");
	}
	
	/**
	 * Destroy the player
	 */
	public void destroy()
	{
		alive = false;
	}
	
	/**
	 * Check if the player is still alive
	 * @return true/false (Alive/Not alive)
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Update the player object
	 */
	public void update()
	{
		if(moving) {
			movingProgress+=movingSpeed;
			if(movingProgress>=cont.getSquareSize()) {
				movingProgress=0;
				moving=false;
				switch(movingDirection) {
					case Direction.LEFT:
						x--;
						break;
					case Direction.RIGHT:
						x++;
						break;
					case Direction.UP:
						y--;
						break;
					case Direction.DOWN:
						y++;
						break;
					default:
						break;
				}
			}
		}
	}
	
	/**
	 * Draw the player
	 * @param g The Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		int dx = cont.getDrawingPositionX(x);
		int dy = cont.getDrawingPositionY(y);
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					dx-=movingProgress;
					break;
				case Direction.RIGHT:
					dx+=movingProgress;
					break;
				case Direction.UP:
					dy-=movingProgress;
					break;
				case Direction.DOWN:
					dy+=movingProgress;
					break;
				default:
					break;
			}
		}
		g.drawImage(img,dx,dy,null);
		//g.setColor(Color.cyan);
		//g.fillRect(dx+cont.getSquareSize()/4,dy+cont.getSquareSize()/4,cont.getSquareSize()/2,cont.getSquareSize()/2);
	}
	
	/**
	 * Get the last stable x position of the player
	 * @return The X position (block coordinate)
	 */
	public int getPosX()
	{
		return x;
	}

	/**
	 * Get the last stable y position of the player
	 * @return The Y position (block coordinate)
	 */
	public int getPosY()
	{
		return y;
	}
	

	/**
	 * Get the x position which the player i moving towards
	 * @return The X position (block coordinate)
	 */
	public int getMovingPosX()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					return x-1;
				case Direction.RIGHT:
					return x+1;
				case Direction.UP:
				case Direction.DOWN:
				default:
					return x;
			}
		}else {
			return x;
		}
	}

	/**
	 * Get the y position which the player i moving towards
	 * @return The Y position (block coordinate)
	 */
	public int getMovingPosY()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
				case Direction.RIGHT:
					return y;
				case Direction.UP:
					return y-1;
				case Direction.DOWN:
					return y+1;
				default:
					return y;
			}
		}else {
			return y;
		}
	}

	/**
	 * Get the x position which the player is drawed on
	 * @return The X position (pixel coordinate)
	 */
	public int getDrawingPosX()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
					return cont.getDrawingPositionX(x)-movingProgress;
				case Direction.RIGHT:
					return cont.getDrawingPositionX(x)+movingProgress;
				case Direction.UP:
				case Direction.DOWN:
				default:
					return cont.getDrawingPositionX(x);
			}
		}else {
			return cont.getDrawingPositionX(x);
		}
	}

	/**
	 * Get the y position which the player drawed on
	 * @return The Y position (pixel coordinate)
	 */
	public int getDrawingPosY()
	{
		if(moving) {
			switch(movingDirection) {
				case Direction.LEFT:
				case Direction.RIGHT:
					return cont.getDrawingPositionY(y);
				case Direction.UP:
					return cont.getDrawingPositionY(y)-movingProgress;
				case Direction.DOWN:
					return cont.getDrawingPositionY(y)+movingProgress;
				default:
					return cont.getDrawingPositionY(y);
			}
		}else {
			return cont.getDrawingPositionY(y);
		}
	}
}