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
}