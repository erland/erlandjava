package erland.game;

/**
 * Specifies all possible directions
 */
public abstract class Direction
{
	/**
	 * Returns the opposite direction
	 * @param direction The direction to return the opposite for
	 * @return The opposite direction
	 */
	public static int opposite(int direction)
	{
		switch(direction) {
			case Direction.LEFT:
				return Direction.RIGHT;
			case Direction.RIGHT:
				return Direction.LEFT;
			case Direction.UP:
				return Direction.DOWN;
			case Direction.DOWN:
				return Direction.UP;
			default:
				return Direction.LEFT;
		}
	}
	public static final int LEFT=0;
	public static final int RIGHT=1;
	public static final int UP=2;
	public static final int DOWN=3;
}
