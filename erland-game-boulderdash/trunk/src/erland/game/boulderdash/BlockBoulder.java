package erland.game.boulderdash;
import java.awt.*;
import erland.util.*;
import erland.game.*;

/**
 * Represents a block on the game area
 */
 
class BlockBoulder extends Block
{
	/** Indicates that the block is moving */
	protected boolean moving;
	
	/** Indicates the block moving direction of the block, see {@link erland.game#Direction} */
	protected int movingDirection;
	
	/** Indicates the moving progress in the direction specified in {@link #movingDirection} */
	protected float movingProgress;
	
	/** Indicates the speed the block is moved with */
	protected float moveSpeed;
	
	/** Indicates the fall height when the block is falling down */
	protected int fallHeight;

	/** Image of the block */
	protected Image img;
	
	/** Falling speed */
	protected static float FALL_SPEED = 1.5f;
	
	public void init(BoulderDashContainerInterface c, ImageHandlerInterface images, BlockContainerInterface cont, int x, int y)
	{
		super.init(c,images,cont,x,y);
		img = images.getImage("boulder.gif");
	}

	public void update()
	{
		if(moving) {
			movingProgress+=moveSpeed;
			if(movingProgress>=cont.getSquareSize()) {
				moving = false;
				movingProgress=0;
				int newX = x;
				int newY = y;
				switch(movingDirection) {
					case Direction.LEFT:
						newX--;
						break;
					case Direction.RIGHT:
						newX++;
						break;
					case Direction.UP:
						newY--;
						break;
					case Direction.DOWN:
						fallHeight++;
						newY++;
						break;
					default:
						break;
				}
				c.setBlockPos(x,y,newX,newY);
			}
		}else {
			fallHeight=0;
		}
		if(!moving) {
			if(c.isFree(x,y+1)) {
				c.moveBlock(x,y,Direction.DOWN,FALL_SPEED);
			}else if(fallHeight>0 && c.isDestroyable(x,y+1)) {
				c.destroyBlock(x,y+1,fallHeight);
				c.moveBlock(x,y,Direction.DOWN,FALL_SPEED);
			}else if(c.isFree(x-1,y) && c.isFree(x-1,y+1) && c.isSlippery(x,y+1)) {
				c.moveBlock(x,y,Direction.LEFT,FALL_SPEED);
			}else if(c.isFree(x+1,y) && c.isFree(x+1,y+1) && c.isSlippery(x,y+1)) {
				c.moveBlock(x,y,Direction.RIGHT,FALL_SPEED);
			}
		}
	}
	
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
		g.setColor(Color.red);
		g.drawImage(img,dx,dy,null);
		//g.fillRect(dx,dy,cont.getSquareSize(),cont.getSquareSize());
	}

	public boolean isMovable()
	{
		return true;	
	}
	
	public boolean move(int direction,float speed)
	{
		if(!moving) {
			if(direction==Direction.DOWN) {
				if(c.isFree(x,y+1)) {
					moving= true;
					moveSpeed = speed;
					movingDirection = Direction.DOWN;
					return true;
				}
			}else if(direction==Direction.LEFT) {
				if(c.isFree(x-1,y)) {
					moving= true;
					moveSpeed = speed;
					movingDirection = Direction.LEFT;
					return true;
				}
			}else if(direction==Direction.RIGHT) { 
				if(c.isFree(x+1,y)) {
					moving = true;
					moveSpeed = speed;
					movingDirection = Direction.RIGHT;
					return true;
				}
			}
		}
		return false;
	}
	public boolean isMoving()
	{
		return moving;
	}
	public boolean isSlippery()
	{
		return true;
	}
}
