package erland.game.boulderdash;

interface BoulderDashContainerInterface
{
	/**
	 * Checks if the specified block position is free
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Position free/Position not free)
	 */
	boolean isFree(int x, int y);
	
	/**
	 * Checks if the specified block position can be destroyed
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Position free/Position not free)
	 */
	boolean isDestroyable(int x, int y);

	/**
	 * Checks if the specified block position contains something that can be killed
	 * {@link #destroyBlock can be called to kill the item at the block position}
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Position killable/Position not killable)
	 */
	boolean isKillable(int x, int y);

	/**
	 * Checks if the specified block position can be digged through
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Possible to dig through/Not possible to dig through)
	 */
	boolean isDigThrough(int x, int y);

	/**
	 * Checks if the block on the specified block position is slippery
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Block slippery/Block not slippery)
	 */
	boolean isSlippery(int x, int y);

	/**
	 * Checks if the block on the specified block position is possible to move
	 * @param x X position to check
	 * @param y Y position to check
	 * @return true/false (Block moveable/Block not moveable)
	 */
	boolean isMovable(int x, int y);

	
	/**
	 * Move the specified block int the specified direction
	 * @param x The x position of the block
	 * @param y The y position of the block
	 * @param direction The direction to move the block in
	 * @param direction The speed to move the block with
	 * @return true/false (Success/Failure)
	 */
	boolean moveBlock(int x, int y, int direction, float speed);
	
	/**
	 * Destroy the specified block in the specified position
	 * @param x The x position of the block
	 * @param y The y position of the block
	 * @param direction The height the destroying block is falling from
	 * @return true/false (Success/Failure)
	 */
	boolean destroyBlock(int x, int y, int height);

	/**
	 * Dig through the specified block in the specified direction
	 * @param x The x position of the block
	 * @param y The y position of the block
	 * @param direction The direction to dig in
	 * @param direction The speed to dig through the block with
	 * @return true/false (Success/Failure)
	 */
	boolean digBlock(int x, int y, int direction, float speed);

	/**
	 * Move the block at the specified position direction to the new position
	 * @param oldX The old x position of the block
	 * @param oldY The old y position of the block
	 * @param newX The new x position of the block
	 * @param newY The new y position of the block
	 * @return true/false (Success/Failure)
	 */
	boolean setBlockPos(int oldX, int oldY, int newX, int newY);
	
	/**
	 * Get the direction which is most suitable if the block want to hit/reach
	 * the player
	 * @param x The block x position
	 * @param y The block y position
	 * @return The most suitable direction if you want to reach the player
	 */
	int getPlayerDirection(int x, int y);

	/**
	 * Remove the block from the game, it does not exist
	 * any more
	 * @param block The block to remove
	 */
	void delBlock(Block block);
	
	/**
	 * Add the specified block to the game at the specified position
	 * @param block The block to add
	 */
	void addBlock(Block block);
	
	/**
	 * Increase number of collected diamonds
	 */
	void increaseDiamonds();
	
	/**
	 * Increase score, the real score added might be different from this
	 * number due to the fact that higher levels gives more score
	 * points
	 * @param score The number of points the score should be increased with
	 */
	void increaseScore(int score);
}