package erland.game.crackout;

import java.awt.*;
/**
 * Implements a monster that will bounce around the game area
 * and collide with the balls
 */
class Monster
	implements CollisionRect
{
	/** Width of the monster */
	protected int sizeX;
	/** Height of the monster */
	protected int sizeY;
	/** X position of the monster */
	protected double x;
	/** Y position of the monster */
	protected double y;
	/** Indicates if the monster is active or not */
	protected boolean active;
	/** Speed of the monster */
	protected int speed;
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** Maximum x position for any part of the monster */
	protected int limitX;
	/** Maximum y position for any part of the monster */
	protected int limitY;
	/** Current moving direction of the monster */
	protected double direction;
	/** Type of monster, see {@link MonsterType} */
	protected int monsterType;
	/** Image handler object */
	protected ImageHandlerInterface images;

	/**
	 * Specifies all available monster types
	 */
	abstract class MonsterType {
		/** Bounce around the game area and collides with the ball, never disappears due to ball hit */
		public static final int bounceBlock=1;
		/** Bounce around the game area and collides with the ball, disappears after the second ball hit */
		public static final int bounceOnceBlock=2;
	}

	/**
	 * Creates a new instance
	 */
	public Monster()
	{
		active=true;
		monsterType = MonsterType.bounceBlock;
	}
	
	/**
	 * Initialize object
	 * @param images Image handler object
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param limitX Maximum x position for any part of the monster
	 * @param limitY Maximum y position for any part of the monster 
	 * @param posX Initial x position of the monster
	 * @param posY Initial y position of the monster
	 * @param direction Initial moving direction of the monster
	 * @param monsterType Type of monster, see {@link MonsterType}
	 */ 
	public void init(ImageHandlerInterface images, int offsetX, int offsetY, int limitX, int limitY, int posX, int posY, double direction, int monsterType)
	{
		x = posX;
		y = posY;
		sizeX = 10;
		sizeY = 10;
		speed=1;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.limitX = limitX;
		this.limitY = limitY;
		this.direction = direction;
		this.monsterType = monsterType;
		active=true;
		this.images = images;
	}
	public int left()
	{
		return (int)x;
	}
	public int right()
	{
		return (int)x+sizeX;
	}
	public int top()
	{
		return (int)y;
	}
	public int bottom()
	{
		return (int)y+sizeY;
	}
	/**
	 * Performs appropriate action if the monster collides with an explosion
	 * @param a Action object which implements all collision actions
	 */
	protected void handleCollisionExplosive(ActionInterface a)
	{
		a.RemoveMonster(this);
		active = false;
	}
	public void handleCollision(ActionInterface a)
	{
		switch(monsterType) {
			case MonsterType.bounceBlock:
				break;
			case MonsterType.bounceOnceBlock:
				active=false;
				a.RemoveMonster(this);
				break;
			default:
				break;
		}
	}
	
	/**
	 * Checks if the monster collides with either the bats and or the walls
	 * and performs the appropriate action
	 * @param bats Array of bats which collision should be checked with
	 * @param a Action object which implements all collision actions
	 * @return The score should be increased with this number
	 */
	protected int handleCollision(Bat bats[], ActionInterface a)
	{
		// Handle collsion with walls
		if(x<0) {
			leftCollision(0);
			x=0;
		}else if(x>(limitX-sizeX)) {
			rightCollision(0);
			x=limitX-sizeX;
		}
		if(y<0) {
			topCollision(0);
			y=0;
		}else if(y>(limitY-sizeY)) {
			bottomCollision(0);
			y=limitY-sizeY;
		}
		normalizeDirection();
		return 0;
	}
	
	/**
	 * Handle a collision on the top of the monster
	 * @param deviation Deviation of bounce normal (Not used)
	 */
	protected void topCollision(double deviation)
	{
		double tmp = direction-2*Math.PI*3/4;
		direction = Math.PI/2-tmp;
	}

	/**
	 * Handle a collision on the bottom of the monster
	 * @param deviation Deviation of bounce normal (Not used)
	 */
	protected void bottomCollision(double deviation)
	{
		double tmp = direction-Math.PI/2;
		direction = 2*Math.PI*3/4-tmp;
	}

	/**
	 * Handle a collision on the left side of the monster
	 * @param deviation Deviation of bounce normal (Not used)
	 */
	protected void leftCollision(double deviation)
	{
		double tmp = direction-Math.PI;
		direction = 2*Math.PI-tmp;
	}

	/**
	 * Handle a collision on the right side of the monster
	 * @param deviation Deviation of bounce normal (Not used)
	 */
	protected void rightCollision(double deviation)
	{
		double tmp = direction;
		direction = Math.PI-tmp;
	}
	
	/**
	 * Normalize the moving direction so it is between 0 and 2*Math.PI
	 */
	protected void normalizeDirection()
	{	
		// Make sure direction is between 0 and 2*PI
		while(direction<0) {
			direction+=(2*Math.PI);
		}
		while(direction>(Math.PI*2)) {
			direction-=(2*Math.PI);
		}
	}

	/**
	 * Move the monster to the next position and checks if it collides with a bat or the
	 * walls and take appropriate action 
	 * @param a Action object which implements the collision actions
	 * @param bats The bats which collision should be checked with
	 * @return The score should be increased with this number
	 */
	public int move(ActionInterface a, Bat bats[])
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			x+=Math.cos(direction);
			y+=Math.sin(direction);
			score+=handleCollision(bats, a);
		}
		return score;
	}
	
	/**
	 * Draw the monster
	 * @param g Grahics object to draw on
	 */
	public void draw(Graphics g)
	{
		if(active) {
			Image img=null;
			switch(monsterType) {
				case MonsterType.bounceBlock:
					img = images.getImage(images.MONSTER_BOUNCEBLOCK);
					break;
				case MonsterType.bounceOnceBlock:
					img = images.getImage(images.MONSTER_BOUNCEONCEBLOCK);
					break;
				default:
					img = images.getImage(images.MONSTER_BOUNCEBLOCK);
					break;
			}
			
			g.drawImage(img,offsetX+(int)x,offsetY+(int)y,null);
			//g.setColor(Color.pink);
			//g.fillRect(offsetX+(int)x,offsetX+(int)y,sizeX, sizeY);
		}
	}
}
