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
	void SafetyWall();
	
	/**
	 * Add a new ball at the current position of
	 * the bat
	 */
	void NewBall();
	
	/**
	 * Add this monster to the list of monsters currently active
	 */
	void NewMonster(Monster m);
	
	/**
	 * Add this feature to the list of features currently active
	 */
	void NewFeature(Feature f);
	
	/**
	 * Remove this ball from the list of active balls
	 */
	void RemoveBall(Ball b);
	
	/**
	 * Remove this monster from the list of active monsters
	 */
	void RemoveMonster(Monster m);
	
	/**
	 * Remove this feature from the list of active features
	 */
	void RemoveFeature(Feature f);
	
	/**
	 * Lock the bat for a while so the user won't be able
	 * to move it
	 */
	void LockBat();
	
	/**
	 * Increase the speed of the balls one step
	 */
	void IncreaseBallSpeed();
	
	/**
	 * Decrease the speed of the balls one step
	 */
	void DecreaseBallSpeed();
	
	/**
	 * Increase the speed of the bat one step
	 */
	void IncreaseBatSpeed();
	
	/**
	 * Decrease the speed of the bat one step
	 */
	void DecreaseBatSpeed();
	
	/**
	 * Increase the life counter with an extra life
	 */
	void ExtraLife();
	
	/**
	 * Add a second bat
	 */
	void DoubleBat();
	
	/**
	 * Increase the missile counter with one
	 */
	void NewMissile();
	
	/**
	 * Make the bat large
	 */
	void LargeBat();
	
	/**
	 * Make the bat small
	 */
	void SmallBat();
	
	/**
	 * Create an explosion at the specified position
	 * with the specified size
	 * @param x The x position of the explosion
	 * @param y The y position of the explosion
	 * @param sizeX The width of the explosion
	 * @param sizeY the height of the explosion
	 */
	void Explode(int x, int y, int sizeX, int sizeY);
}
