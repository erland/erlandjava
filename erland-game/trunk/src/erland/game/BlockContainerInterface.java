package erland.game;

/**
 * Represents a container for blocks
 * @author Erland Isaksson
 */
public interface BlockContainerInterface
{
	/**
	 * Get the X offset which should be used when drawing
	 * @return The X offset in pixels
	 */
	int getOffsetX();
	/**
	 * Get the Y offset which should be used when drawing
	 * @return The Y offset in pixels
	 */
	int getOffsetY();
	/**
	 * Get the number of visible horizontal blocks
	 * @return The number of horizontal blocks
	 */
	int getSizeX();
	/**
	 * Get the number of visible vertical blocks
	 * @return The number of vertical blocks
	 */
	int getSizeY();

	/**
	 * Get the size of a single block
	 * @return The size of a single block in pixels
	 */
	int getSquareSize();

	/**
     * Get the drawing position for a specific block coordinate
     * @param x Block x position (Square coordinate)
     * @return x drawing position in pixels
     */
    int getDrawingPositionX(int x);

	/**
     * Get the drawing position for a specific block coordinate
     * @param y Block y position (Square coordinate)
     * @return y drawing position in pixels
     */
    int getDrawingPositionY(int y);

    /**
     * Get the drawing position for a specific pixel coordinate
     * @param x Object x position (Pixel coordinate)
     * @return x drawing position in pixels
     */
    int getPixelDrawingPositionX(int x);

	/**
     * Get the drawing position for a specific pixel coordinate
     * @param y Object y position (Pixel coordinate)
     * @return y drawing position in pixels
     */
    int getPixelDrawingPositionY(int y);

	/**
     * Get the position for a specific block coordinate
     * @param x Block x position (Square coordinate)
     * @return x position in pixels
     */
    int getPositionX(int x);

	/**
     * Get the position for a specific block coordinate
     * @param y Block y position (Square coordinate)
     * @return y position in pixels
     */
    int getPositionY(int y);

	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    int getDrawingSizeX();

	/**
     * Get the drawing size for container
     * @return x drawing size in pixels
     */
    int getDrawingSizeY();

    /**
     * Checks if a specific block is within the visible area
     * @param posX X position of the block to check (Square coordinate)
     * @param posY Y position of the block to check (Square coordinate)
     * @return true/false (Visible/Invisible)
     */
    boolean getVisible(int posX, int posY);

    /**
	 * Get the horizontal scrolling offset
	 * @return The horizontal scrolling offset
	 */
	public int getScrollingOffsetX();
	/**
	 * Sets the vertical scrolling offset
	 * @return The vertical scrolling offset
	 */
	public int getScrollingOffsetY();

    /**
	 * Get the horizontal scrolling size
	 * @return The horizontal scrolling offset (Pixel coordinates)
	 */
	public int getScrollingSizeX();

	/**
	 * Get the vertical scrolling size
	 * @return The vertical scrolling size (Pixel coordinates)
	 */
	public int getScrollingSizeY();
}
