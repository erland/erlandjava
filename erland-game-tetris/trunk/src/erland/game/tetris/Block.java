package erland.game.tetris;

import java.awt.*;

/**
 * Represents a tetris block, all Block sub classes should
 * be derived from this
 * @author Erland Isaksson
 */
abstract class Block implements Cloneable
{
	/** rotation angle of block */
	protected int rotation;
	/** x position of block(This is a block coordinate and not a pixel coordinate) */
	protected int x;
	/** y position of block(This is a block coordinate and not a pixel coordinate) */
	protected int y;

	/**
	 * Get the color of the block
	 * @return The color of the block
	 */
	public abstract Color getColor();

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    /**
     * Get the rotation of the block
     * @return The rotation of the block 0,1,2,3
     */
    public int getRotation() {
        return rotation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
	/**
	 * Initialize block
	 * @param x x position of block(Block coordinate)
	 * @param y y position of block(Block coordinate)
	 * @param rotation rotation angle of block
	 */
	protected void init(int x, int y, int rotation)
	{
		this.x = x;
		this.y = y; 
		this.rotation = rotation;
	}
	
	/**
	 * Move block once step down
	 * @param m The BlockMatrix in which the block resides
	 * @return true/false (Success/Failure)
	 */
	public boolean moveDown(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x,y+1,rotation)) {
			set(m,x,y+1,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	
	/**
	 * Move the block one step right
	 * @param m The BlockMatrix in which the block resides
	 * @return true/false (Success/Failure)
	 */
	public boolean moveRight(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x+1,y,rotation)) {
			set(m,x+1,y,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}

	/**
	 * Move the block one step left
	 * @param m The BlockMatrix in which the block resides
	 * @return true/false (Success/Failure)
	 */
	public boolean moveLeft(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x-1,y,rotation)) {
			set(m,x-1,y,rotation);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	/**
	 * Rotate the block 90 degrees clockwize
	 * @param m The BlockMatrix in which the block resides
	 * @return true/false (Success/Failure)
	 */
	public boolean rotateRight(BlockMatrix m) 
	{
		unset(m,x,y,rotation);
		if(check(m,x,y,rotation+90)) {
			set(m,x,y,rotation+90);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}
	
	/**
	 * Rotate the block 90 degrees counter clockwize
	 * @param m The BlockMatrix in which the block resides
	 * @return true/false (Success/Failure)
	 */
	public boolean rotateLeft(BlockMatrix m)
	{
		unset(m,x,y,rotation);
		if(check(m,x,y,rotation-90)) {
			set(m,x,y,rotation-90);
			return true;
		}else {
			set(m,x,y,rotation);
			return false;
		}
	}	
	
	/**
	 * Check if it is possible to put the block in the specified position and
	 * rotation angle
	 * @param m The BlockMatrix in which the block resides
	 * @param x The x position which should be checked(Block coordinate)
	 * @param y The y position which should be checked(Block coordinate)
	 * @param rotation The rotation angle which should be checked
	 * @return true/false (Success/Failure)
	 */
	protected abstract boolean check(BlockMatrix m, int x, int y, int rotation);

	/**
	 * Put the block in the specified position and rotation angle
	 * @param m The BlockMatrix in which the block resides
	 * @param x The x position (Block coordinate)
	 * @param y The y position (Block coordinate)
	 * @param rotation The rotation angle
	 */
	protected abstract void set(BlockMatrix m, int x, int y, int rotation);

	/**
	 * Remove the block from the specified position and rotation angle
	 * @param m The BlockMatrix in which the block resides
	 * @param x The x position (Block coordinate)
	 * @param y The y position (Block coordinate)
	 * @param rotation The rotation angle
	 */
	protected abstract void unset(BlockMatrix m, int x, int y, int rotation);
	
}