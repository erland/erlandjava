package erland.game.crackout;

/**
 * Specifies all actions that can occur
 * due to various collisions between objects
 */
interface ActionInterface
{
	/**
	 * A safety wall at the bottom so the ball
	 * just bounce when you miss it with the bat
	 * and it hits the bottom of the game area
	 */
	void safetyWall();
	
	/**
	 * Add a new ball at the current position of
	 * the bat
	 */
	void newBall();
	
	/**
	 * Add this monster to the list of monsters currently active
	 */
	void newMonster(Monster m);
	
	/**
	 * Add this feature to the list of features currently active
	 */
	void newFeature(Feature f);
	
	/**
	 * Remove this ball from the list of active balls
	 */
	void removeBall(Ball b);
	
	/**
	 * Remove this monster from the list of active monsters
	 */
	void removeMonster(Monster m);
	
	/**
	 * Remove this feature from the list of active features
	 */
	void removeFeature(Feature f);
	
	/**
	 * Lock the bat for a while so the user won't be able
	 * to move it
	 */
	void lockBat();
	
	/**
	 * Increase the speed of the balls one step
	 */
	void increaseBallSpeed();
	
	/**
	 * Decrease the speed of the balls one step
	 */
	void decreaseBallSpeed();
	
	/**
	 * Increase the speed of the bat one step
	 */
	void increaseBatSpeed();
	
	/**
	 * Decrease the speed of the bat one step
	 */
	void decreaseBatSpeed();
	
	/**
	 * Increase the life counter with an extra life
	 */
	void extraLife();
	
	/**
	 * Add a second bat
	 */
	void doubleBat();
	
	/**
	 * Increase the missile counter with one
	 */
	void newMissile();
	
	/**
	 * Make the bat large
	 */
	void largeBat();
	
	/**
	 * Make the bat small
	 */
	void smallBat();
	
	/**
	 * Create an explosion at the specified position
	 * with the specified size
	 * @param x The x position of the explosion
	 * @param y The y position of the explosion
	 * @param sizeX The width of the explosion
	 * @param sizeY the height of the explosion
	 */
	void explode(int x, int y, int sizeX, int sizeY);
}
