package erland.game.crackout;

/**
 * Refers to a rectangular object that collisions can occur with
 */
interface CollisionRect
{
	/**
	 * Left side position of the object
	 * @return Left side position (Pixel coordinate)
	 */
	int left();

	/**
	 * Right side position of the object
	 * @return Right side position (Pixel coordinate)
	 */
	int right();

	/**
	 * Top side position of the object
	 * @return Top side position (Pixel coordinate)
	 */
	int top();

	/**
	 * Bottom side position of the object
	 * @return Bottom side position (Pixel coordinate)
	 */
	int bottom();

	/**
	 * Performs the action that should occur when something hits the object
	 * @param a Reference to action object that implements all collision actions
	 */
	void handleCollision(ActionInterface a);
}
