package erland.game.pipes;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
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
	/** Pipe parts in this pipe block */
	protected PipePart parts[][];
	/** X position of pipe block (Block coordinate) */
	protected int x;
	/** Y position of pipe block (Block coordinate) */
	protected int y;
	/** Previous X position of pipe block (Block coordinate) */
	protected int oldX;
	/** Previous Y position of pipe block (Block coordinate) */
	protected int oldY;
    /** Old previous X position of pipe block (Block coordinate) */
    protected int prevOldX;
    /** Old previous Y position of pipe block (Block coordinate) */
    protected int prevOldY;
	/** Block container which the block resides in */
	protected BlockContainerInterface cont;
	/** List with pipe parts in the block which have moving water */
	protected LinkedList partsWithMovingWater;
	/** List with new pipe parts in the block which have moving water */
	protected LinkedList addPartsWithMovingWater;
	/** List with pipe parts in the block which no longer have moving water */
	protected LinkedList delPartsWithMovingWater;
	/** Height and width of the pipe block (Number of pipe parts) */
	protected static final int size=3;
	/** Indicates if the block is moving */
	protected boolean moving;
	/** Indicates moving direction of block, see {@link Direction} */
	protected int movingDirection;
	/** Indicates moving progress (Number of pixels moved in direction {@link #movingDirection} */
	protected int movingProgress;
	/** Indicates if the pipe block has at least one part which contains water */
	protected boolean hasWater;
	/** Indicates if the block has been drawn since redraw flag was last set*/
	protected int redrawsLeft;
	
	/**
	 * Initialize pipe block
	 * @param cont Block container which the block resides in
	 * @param parts Matrix of all pipe parts in the block
	 * @param x X position of block (Block coordinate)
	 * @param y Y position of block (Block coordinate)
	 */
	public void init(BlockContainerInterface cont, PipePart parts[][], int x, int y)
	{
		this.cont = cont;
		this.parts = parts;
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
        this.prevOldX = x;
        this.prevOldY = y;
		this.partsWithMovingWater = new LinkedList();
		this.addPartsWithMovingWater = new LinkedList();
		this.delPartsWithMovingWater = new LinkedList();
		for (int i=0; i<parts.length; i++) {
			for (int j=0; j<parts[0].length; j++) {
				parts[i][j].init(this, i,j);
		    }
	    }
	    hasWater=false;
	    redrawsLeft = 2;
	}

	/**
	 * Set position of pipe block
	 * @param x X position of block (Block coordinate)
	 * @param y Y position of block (Block coordinate)
	 */
	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Check if the pipe block is currently moving
	 * @return true/false (Moving/Not moving)
	 */
	public boolean isMoving()
	{
		return moving;
	}
	
	/**
	 * Move the pipe block once step up
	 * @param c Interface to block container
	 **/
	public void moveUp(PipeBlockContainerInterface c)
	{
		if(!moving && isMovable() && c.isFreePos(x,y-1)) {
			moving=true;
			movingDirection=Direction.UP;
			movingProgress=0;
		}
	}

	/**
	 * Move the pipe block once step down
	 * @param c Interface to block container
	 **/
	public void moveDown(PipeBlockContainerInterface c)
	{
		if(!moving && isMovable() && c.isFreePos(x,y+1)) {
			moving=true;
			movingDirection=Direction.DOWN;
			movingProgress=0;
		}
	}

	/**
	 * Move the pipe block once step left
	 * @param c Interface to block container
	 **/
	public void moveLeft(PipeBlockContainerInterface c)
	{
		if(!moving && isMovable() && c.isFreePos(x-1,y)) {
			moving=true;
			movingDirection=Direction.LEFT;
			movingProgress=0;
		}
	}

	/**
	 * Move the pipe block once step right
	 * @param c Interface to block container
	 **/
	public void moveRight(PipeBlockContainerInterface c)
	{
		if(!moving && isMovable() && c.isFreePos(x+1,y)) {
			moving=true;
			movingDirection=Direction.RIGHT;
			movingProgress=0;
		}
	}

	/**
	 * Get x position of pipe block
	 * @return X position of pipe block (Block coordinate)
	 */
	public int getPosX()
	{
		return x;
	}
	/**
	 * Get y position of pipe block
	 * @return Y position of pipe block (Block coordinate)
	 */
	public int getPosY()
	{
		return y;
	}
	
	/**
	 * Get x drawing position of pipe block
	 * @return X drawing position of pipe block (Pixel coordinate)
	 */
	public int getMovingDrawingPosX()
	{
		int dx=0;
		if(moving) {
			if(movingDirection==Direction.LEFT) {
				dx= -movingProgress;
			}else if(movingDirection==Direction.RIGHT) {
				dx= movingProgress;
			}
		}
		return cont.getDrawingPositionX(x)+dx;
	}
	
	/**
	 * Get y drawing position of pipe block
	 * @return Y drawing position of pipe block (Pixel coordinate)
	 */
	public int getMovingDrawingPosY()
	{
		int dy=0;
		if(moving) {
			if(movingDirection==Direction.UP) {
				dy= -movingProgress;
			}else if(movingDirection==Direction.DOWN) {
				dy= movingProgress;
			}
		}
		return cont.getDrawingPositionY(y)+dy;
	}
	
	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    public int getDrawingSizeX()
    {
    	return getSquareSize()*getSizeX();
    }
    
	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    public int getDrawingSizeY()
    {
    	return getSquareSize()*getSizeY();
    }
	
	/**
	 * Draw the pipe block
	 * @param g Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		g.clearRect(cont.getDrawingPositionX(prevOldX),cont.getDrawingPositionY(prevOldY),cont.getSquareSize(),cont.getSquareSize());
        if(oldX!=prevOldX || oldY!=prevOldY) {
            g.clearRect(cont.getDrawingPositionX(oldX),cont.getDrawingPositionY(oldY),cont.getSquareSize(),cont.getSquareSize());
        }
		for (int x=0; x<parts.length; x++) {
			for (int y=0; y<parts[0].length; y++) {
				parts[x][y].draw(g);
		    }
	    }
        if(redrawsLeft>0) {
            redrawsLeft--;
        }
	}
	
	/**
	 * Add water to the pipe block in the specified part and side of that part
	 * @param x X position of part to add water in (Part coordinate)
	 * @param y Y position of part to add water in (Part coordinate)
	 * @param direction Side of part to add water on, see {@link Direction}
	 * @return <code>true</code> - Water successfully added
	 * <br><code>false</code> - It was not possible to add water in this part on the specified side
	 */
	public boolean initWater(int x, int y, int direction)
	{
		if(parts[x][y].initWater(direction)) {
			hasWater=true;
			addPartsWithMovingWater.add(parts[x][y]);
			return true;
		}
		return false;
	}
	
	/**
	 * Check if block has any parts with moving water
	 * @return true/false (Moving water/No moving water)
	 */
	public boolean hasMovingWater()
	{
		if(partsWithMovingWater.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Update pipe block.
	 * Add new parts with moving water and remove parts with no longer
	 * moving water. If the block is moving, move it some pixels in the
	 * moving direction
	 */
	public void updateBlock()
	{
		ListIterator it = addPartsWithMovingWater.listIterator();
		while(it.hasNext()) {
			PipePart part = (PipePart)(it.next());
			Log.println(this,"addBlock: " + part.toString());
			partsWithMovingWater.add(part);
			setRedraw(true);
		}
		addPartsWithMovingWater.clear();
		it = delPartsWithMovingWater.listIterator();
		while(it.hasNext()) {
			PipePart part = (PipePart)(it.next());
			Log.println(this,"delBlock: " + part.toString());
			partsWithMovingWater.remove(part);
			setRedraw(true);
		}
		delPartsWithMovingWater.clear();

        prevOldX = oldX;
        prevOldY = oldY;
		oldX = x;
		oldY = y;
		if(moving) {
			setRedraw(true);
			movingProgress+=7;
			if(movingProgress>cont.getSquareSize()) {
				moving = false;
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
	 * Move all moving water in the pipe block one step forward
	 * @param container Interface to block container which the pipe block resides in
	 */
	public void moveWater(PipeBlockContainerInterface container)
	{
		ListIterator it = partsWithMovingWater.listIterator();
		if(it.hasNext()) {
			setRedraw(true);
			PipePart part = (PipePart)(it.next());
			moveWaterInPart(container,part);
			if(partsWithMovingWater.contains(part)) {
				partsWithMovingWater.remove(part);
				partsWithMovingWater.add(part);
			}
		}
	}

	/**
	 * Get the score that should be added when the part in the specified position has
	 * been filled with water
	 * @param x X Position of the part
	 * @param y Y Position of the part
	 * @return Score that should be added
	 */
	protected int getScore(int x, int y)
	{
		return 10;
	}
	
	/**
	 * Checks if a filled part at the specified position should be added to the number of
	 * filled parts
	 * @param x X Position of the part
	 * @param y Y Position of the part
	 * @return true/false (Should be added/Should not be added)
	 */
	protected boolean addFilledPart(int x, int y)
	{
		return true;
	}
	/**
	 * Move water in a single part in the block
	 * @param container Interface to block container which the pipe block with the part resides in
	 * @param part The part to move water in
	 */
	protected void moveWaterInPart(PipeBlockContainerInterface container, PipePart part)
	{
		if(!part.moveWater()) {
			delPartsWithMovingWater.add(part);
			container.addScore(getScore(part.getXPosition(),part.getYPosition()));
			if(addFilledPart(part.getXPosition(),part.getYPosition())) {
				container.addFilledPart();
			}
			if(part.leakWater(Direction.LEFT)) {
				if(part.getXPosition()>0) {
					Log.println(this,toString() + ":initWater Left");
					initWater(part.getXPosition()-1,part.getYPosition(),Direction.RIGHT);
				}else {
					if(x>0) {
						Log.println(this,toString() + ":addWater Left:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
						container.addWater(this.x-1,this.y,parts.length-1,part.getYPosition(),Direction.RIGHT);
					}
				}
			}
			if(part.leakWater(Direction.RIGHT)) {
				if(part.getXPosition()<(parts.length-1)) {
					Log.println(this,toString() + ":initWater Right");
					initWater(part.getXPosition()+1,part.getYPosition(),Direction.LEFT);
				}else {
					if(x<(cont.getSizeX()-1)) {
						Log.println(this,toString() + ":addWater Right:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
						container.addWater(this.x+1,this.y,0,part.getYPosition(),Direction.LEFT);
					}
				}
			}
			if(part.leakWater(Direction.UP)) {
				if(part.getYPosition()>0) {
					Log.println(this,toString() + ":initWater Up");
					initWater(part.getXPosition(),part.getYPosition()-1,Direction.DOWN);
				}else {
					if(y>0) {
						Log.println(this,toString() + ":addWater Up:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
						container.addWater(this.x,this.y-1,part.getXPosition(),parts[0].length-1,Direction.DOWN);
					}
				}
			}
			if(part.leakWater(Direction.DOWN)) {
				if(part.getYPosition()<(parts[0].length-1)) {
					Log.println(this,toString() + ":initWater Down");
					initWater(part.getXPosition(),part.getYPosition()+1,Direction.UP);
				}else {
					if(y<(cont.getSizeY()-1)) {
						Log.println(this,toString() + ":addWater Down:" + x + "," + y + ": " + part.getXPosition() + ", " + part.getYPosition());
						container.addWater(this.x,this.y+1,part.getXPosition(),0,Direction.UP);
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
			if(movingDirection==Direction.LEFT) {
				dx= -movingProgress;
			}else if(movingDirection==Direction.RIGHT) {
				dx= movingProgress;
			}
		}
    	return getSquareSize()*x+dx;
    }
    public int getPositionY(int y)
    {
		int dy=0;
		if(moving) {
			if(movingDirection==Direction.UP) {
				dy= -movingProgress;
			}else if(movingDirection==Direction.DOWN) {
				dy= movingProgress;
			}
		}
    	return getSquareSize()*y +dy;
    }
    public int getPixelDrawingPositionX(int x) {
        return getOffsetX()-getScrollingOffsetX()+x;
    }

    public int getPixelDrawingPositionY(int y) {
        return getOffsetY()-getScrollingOffsetY()+y;
    }

    public boolean getVisible(int posX, int posY) {
        return true;
    }

    public int getScrollingOffsetX() {
        return 0;
    }

    public int getScrollingOffsetY() {
        return 0;
    }

    public int getScrollingSizeX() {
        return getSizeX()*getSquareSize();
    }

    public int getScrollingSizeY() {
        return getSizeY()*getSquareSize();
    }

	/**
	 * Indicates if this block is full with water
	 * @return true/false (Full/Not full)
	 */
	public boolean isWaterFilled()
	{
		boolean filled=true;
		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				if(!parts[x][y].isWaterFilled()) {
					filled = false;
				}
			}
	    }
	    return filled;
	}

	/** 
	 * Checks if it is possible to move this block
	 * @return true/false (Movable/Not movable)
	 */
	protected boolean isMovable()
	{
		return !hasWater;
	}
	
	/**
	 * Initialize pipe block
	 * @param cont Block container which the block resides in
	 * @param x X position of block (Block coordinate)
	 * @param y Y position of block (Block coordinate)
	 */
	abstract public void init(BlockContainerInterface cont, int x, int y);
	
	/**
	 * Checks if the block needs to be redrawn
	 */
	public boolean needRedraw()
	{
		return redrawsLeft>0;
	}
	
	/**
	 * Sets the flag if the block should be redrawn or not
	 * @param redraw true if the block needs to be redrawn
	 */
	public void setRedraw(boolean redraw)
	{
        if(redraw) {
            redrawsLeft=2;
        }
	}
}
