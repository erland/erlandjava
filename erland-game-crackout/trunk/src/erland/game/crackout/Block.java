package erland.game.crackout;
import erland.game.*;
import java.awt.*;

abstract class Block
	implements CollisionRect
{
	int sizeX;
	int sizeY;
	int posX;
	int posY;
	boolean active;
	String description;
	ImageHandlerInterface images;
	BlockContainerInterface cont;
	
	abstract public Object clone()
    	throws CloneNotSupportedException;
	void init(ImageHandlerInterface images, BlockContainerInterface cont, int sizeX, int sizeY, int posX, int posY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = posX;
		this.posY = posY;
		this.images = images;
		this.cont = cont;
		active = true;
	} 
	
	public void setContainer(BlockContainerInterface cont)
	{
		this.cont = cont;
	}	
	
	public BlockContainerInterface getContainer()
	{
		return cont;
	}
	public void setPosition(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	public int getPositionX()
	{
		return posX;
	}
	
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
	public void handleCollision(ActionInterface a)
	{
		active=false;
	}
	public void handleCollisionExplosive(ActionInterface a)
	{
		active=false;
	}
	
	boolean isActive()
	{
		return active;
	}
	String getDescription()
	{
		return description;
	}
	public abstract void setColor(Color color);
	public abstract Color getColor();
	public abstract int getScore();
	public abstract boolean isRemovable();
	public abstract void draw(Graphics g);
}
