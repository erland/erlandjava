package erland.game.pipes;
/**
 * Specifies all actions possible to request from the pipe block container
 */
interface PipeBlockContainerInterface
{
	/**
	 * Add moving water to the specified block if possible
	 * @param blockX X position of block
	 * @param blockY Y position of block
	 * @param partX X position of part within block where water should be added
	 * @param partY Y position of part within block where water should be added
	 * @param direction The side of the block where water enters, see {@link PipePart.Direction}
	 */
	void addWater(int blockX, int blockY, int partX, int partY, int direction);
	
	/**
	 * Checks if a specific block position is free, so a block can
	 * be moved to it
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Free/Not free)
	 */
	boolean isFreePos(int x, int y);
}
