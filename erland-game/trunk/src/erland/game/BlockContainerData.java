package erland.game;

/**
 * Represents a container for blocks
 * @author Erland Isaksson
 */
public class BlockContainerData
	implements BlockContainerInterface
{
	/**
	 * The X offset in pixels
	 */
	protected int offsetX;
	
	/**
	 * The Y offset in pixels
	 */
	protected int offsetY;
	
	/**
	 * Number of horizontal blocks
	 */
	protected int sizeX;
	
	/**
	 * Number of vertical blocks
	 */
	protected int sizeY;

	/**
	 * The size of a single block in pixels
	 */
	protected int squareSize;

	/**
	 * Creates a new object
	 * @param offsetX The X offset in pixels
	 * @param offsetY The Y offset in pixels
	 * @param sizeX Number of horizontal blocks
	 * @param sizeY Number of vertical blocks
	 * @param squareSize Size of a single block in pixels
	 */
	public BlockContainerData(int offsetX, int offsetY, int sizeX, int sizeY, int squareSize)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.squareSize = squareSize;
	}
	public int getOffsetX()
	{
		return offsetX;
	}
	public int getOffsetY()
	{
		return offsetY;
	}
	public int getSizeX()
	{
		return sizeX;
	}
	public int getSizeY()
	{
		return sizeY;
	}
	public int getSquareSize()
	{
		return squareSize;
	}
    public int getDrawingPositionX(int x)
    {
    	return getOffsetX() + getSquareSize()*x;
    }
    public int getDrawingPositionY(int y)
    {
    	return getOffsetY() + getSquareSize()*y;
    }
    public int getPositionX(int x)
    {
    	return getSquareSize()*x;
    }
    public int getPositionY(int y)
    {
    	return getSquareSize()*y;
    }
    public int getDrawingSizeX()
    {
    	return getSquareSize()*getSizeX();
    }
    public int getDrawingSizeY()
    {
    	return getSquareSize()*getSizeY();
    }
	/**
	 * Sets the horizontal drawing offset
	 * @param x The new X drawing offset
	 */
	public void setOffsetX(int offsetX)
	{
		this.offsetX = offsetX;
	}
	/**
	 * Sets the horizontal drawing offset
	 * @param y The new Y drawing offset
	 */
	public void setOffsetY(int offsetY)
	{
		this.offsetY = offsetY;
	}
}