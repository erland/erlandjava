package erland.game.pipes;
import java.awt.Graphics;
import java.util.*;
import erland.game.*;
import erland.util.*;
/**
 * Represents a block with a number of pipe parts
 */
abstract class PipeBlock
	implements BlockContainerInterface
{
	protected PipePart parts[][];
	protected int x;
	protected int y;
	protected BlockContainerInterface cont;
	protected LinkedList partsWithMovingWater;
	protected LinkedList addPartsWithMovingWater;
	protected LinkedList delPartsWithMovingWater;
	protected static final int size=3;
	protected boolean moving;
	protected int movingDirection;
	protected int movingProgress;
	protected boolean hasWater;
	
	public void init(BlockContainerInterface cont, PipePart parts[][], int x, int y)
	{
		this.cont = cont;
		this.parts = parts;
		this.x = x;
		this.y = y;
		this.partsWithMovingWater = new LinkedList();
		this.addPartsWithMovingWater = new LinkedList();
		this.delPartsWithMovingWater = new LinkedList();
		for (int i=0; i<parts.length; i++) {
			for (int j=0; j<parts[0].length; j++) {
				parts[i][j].init(this, i,j);
		    }
	    }
	    hasWater=false;
	}

	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public void moveUp(PipeBlockContainerInterface c)
	{
		if(!moving && !hasWater && c.isFreePos(x,y-1)) {
			moving=true;
			movingDirection=PipePart.Direction.UP;
			movingProgress=0;
		}
	}

	public void moveDown(PipeBlockContainerInterface c)
	{
		if(!moving && !hasWater && c.isFreePos(x,y+1)) {
			moving=true;
			movingDirection=PipePart.Direction.DOWN;
			movingProgress=0;
		}
	}
	public void moveLeft(PipeBlockContainerInterface c)
	{
		if(!moving && !hasWater && c.isFreePos(x-1,y)) {
			moving=true;
			movingDirection=PipePart.Direction.LEFT;
			movingProgress=0;
		}
	}
	public void moveRight(PipeBlockContainerInterface c)
	{
		if(!moving && !hasWater && c.isFreePos(x+1,y)) {
			moving=true;
			movingDirection=PipePart.Direction.RIGHT;
			movingProgress=0;
		}
	}

	public int getPosX()
	{
		return x;
	}
	public int getPosY()
	{
		return y;
	}
	
	public int getMovingDrawingPosX()
	{
		int dx=0;
		if(moving) {
			if(movingDirection==PipePart.Direction.LEFT) {
				dx= -movingProgress;
			}else if(movingDirection==PipePart.Direction.RIGHT) {
				dx= movingProgress;
			}
		}
		return cont.getDrawingPositionX(x)+dx;
	}
	
	public int getMovingDrawingPosY()
	{
		int dy=0;
		if(moving) {
			if(movingDirection==PipePart.Direction.UP) {
				dy= -movingProgress;
			}else if(movingDirection==PipePart.Direction.DOWN) {
				dy= movingProgress;
			}
		}
		return cont.getDrawingPositionY(y)+dy;
	}
	
	public void draw(Graphics g)
	{
		for (int x=0; x<parts.length; x++) {
			for (int y=0; y<parts[0].length; y++) {
				parts[x][y].draw(g);
		    }
	    }
	}
	public boolean initWater(int x, int y, int direction)
	{
		//System.out.println(toString() + ":initWater : " + String.valueOf(x) + ", " + String.valueOf(y) + ", " + direction);
		if(parts[x][y].isOpen(direction)) {
			if(!parts[x][y].hasWater(direction)) {
				if(parts[x][y].initWater(direction)) {
					hasWater=true;
					addPartsWithMovingWater.add(parts[x][y]);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasMovingWater()
	{
		if(partsWithMovingWater.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	public void updateBlock()
	{
		ListIterator it = addPartsWithMovingWater.listIterator();
		while(it.hasNext()) {
			PipePart part = (PipePart)(it.next());
			//System.out.println("add: " + part.toString());
			partsWithMovingWater.add(part);
		}
		addPartsWithMovingWater.clear();
		it = delPartsWithMovingWater.listIterator();
		while(it.hasNext()) {
			PipePart part = (PipePart)(it.next());
			//System.out.println("remove: " + part.toString());
			partsWithMovingWater.remove(part);
		}
		delPartsWithMovingWater.clear();
		
		if(moving) {
			movingProgress+=7;
			if(movingProgress>cont.getSquareSize()) {
				moving = false;
				switch(movingDirection) {
					case PipePart.Direction.LEFT:
						x--;
						break;
					case PipePart.Direction.RIGHT:
						x++;
						break;
					case PipePart.Direction.UP:
						y--;
						break;
					case PipePart.Direction.DOWN:
						y++;
						break;
					default:
						break;
				}
			}
		}
	}
	public void moveWater(PipeBlockContainerInterface container)
	{
		ListIterator it = partsWithMovingWater.listIterator();
		while(it.hasNext()) {
			PipePart part = (PipePart)(it.next());
			if(!part.moveWater()) {
				delPartsWithMovingWater.add(part);
				if(part.leakWater(PipePart.Direction.LEFT)) {
					if(part.getXPosition()>0) {
						//System.out.println(toString() + ":initWater Left");
						initWater(part.getXPosition()-1,part.getYPosition(),PipePart.Direction.RIGHT);
					}else {
						if(x>0) {
							//System.out.println(toString() + ":addWater Left:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
							container.addWater(this.x-1,this.y,parts.length-1,part.getYPosition(),PipePart.Direction.RIGHT);
						}
					}
				}
				if(part.leakWater(PipePart.Direction.RIGHT)) {
					if(part.getXPosition()<(parts.length-1)) {
						//System.out.println(toString() + ":initWater Right");
						initWater(part.getXPosition()+1,part.getYPosition(),PipePart.Direction.LEFT);
					}else {
						if(x<(cont.getSizeX()-1)) {
							//System.out.println(toString() + ":addWater Right:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
							container.addWater(this.x+1,this.y,0,part.getYPosition(),PipePart.Direction.LEFT);
						}
					}
				}
				if(part.leakWater(PipePart.Direction.UP)) {
					if(part.getYPosition()>0) {
						//System.out.println(toString() + ":initWater Up");
						initWater(part.getXPosition(),part.getYPosition()-1,PipePart.Direction.DOWN);
					}else {
						if(y>0) {
							//System.out.println(toString() + ":addWater Up:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
							container.addWater(this.x,this.y-1,part.getXPosition(),parts[0].length-1,PipePart.Direction.DOWN);
						}
					}
				}
				if(part.leakWater(PipePart.Direction.DOWN)) {
					if(part.getYPosition()<(parts[0].length-1)) {
						//System.out.println(toString() + ":initWater Down");
						initWater(part.getXPosition(),part.getYPosition()+1,PipePart.Direction.UP);
					}else {
						if(y<(cont.getSizeY()-1)) {
							//System.out.println(toString() + ":addWater Down:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
							container.addWater(this.x,this.y+1,part.getXPosition(),0,PipePart.Direction.UP);
						}
					}
				}
			}
		}
	}

	public int getOffsetX()
	{
		return getMovingDrawingPosX();
	}
	public int getOffsetY()
	{
		return getMovingDrawingPosY();
	}
	public int getSizeX()
	{
		return size;
	}
	public int getSizeY()
	{
		return size;
	}
	public int getSquareSize()
	{
		return cont.getSquareSize()/size;
	}
    public int getDrawingPositionX(int x)
    {
    	return getOffsetX()+ getSquareSize()*x;
    }
    public int getDrawingPositionY(int y)
    {
    	return getOffsetY()+ getSquareSize()*y;
    }
    public int getPositionX(int x)
    {
		int dx=0;
		if(moving) {
			if(movingDirection==PipePart.Direction.LEFT) {
				dx= -movingProgress;
			}else if(movingDirection==PipePart.Direction.RIGHT) {
				dx= movingProgress;
			}
		}
    	return getSquareSize()*x+dx;
    }
    public int getPositionY(int y)
    {
		int dy=0;
		if(moving) {
			if(movingDirection==PipePart.Direction.UP) {
				dy= -movingProgress;
			}else if(movingDirection==PipePart.Direction.DOWN) {
				dy= movingProgress;
			}
		}
    	return getSquareSize()*y +dy;
    }

	abstract public void init(BlockContainerInterface cont, int x, int y);
}
