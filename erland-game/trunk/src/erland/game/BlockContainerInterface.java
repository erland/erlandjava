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
	 * Get the number of horizontal blocks
	 * @return The number of horizontal blocks
	 */
	int getSizeX();
	/**
	 * Get the number of vertical blocks
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
}