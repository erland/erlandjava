package erland.game.crackout;

import java.awt.*;

class Monster
	implements CollisionRect
{
	int sizeX;
	int sizeY;
	double x;
	double y;
	boolean active;
	int speed;
	int offsetX;
	int offsetY;
	int limitX;
	int limitY;
	double direction;
	int monsterType;
	ImageHandlerInterface images;

	class MonsterType {
		static final int bounceBlock=1;
		static final int bounceOnceBlock=2;
	}

	Monster()
	{
		active=true;
		monsterType = MonsterType.bounceBlock;
	}
	void init(ImageHandlerInterface images, int offsetX, int offsetY, int limitX, int limitY, int posX, int posY, double direction, int monsterType)
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
	public void handleCollisionExplosive(ActionInterface a)
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
	int handleCollision(Bat bats[], ActionInterface a)
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

	void topCollision(double deviation)
	{
		double tmp = direction-2*Math.PI*3/4;
		direction = Math.PI/2-tmp;
	}

	void bottomCollision(double deviation)
	{
		double tmp = direction-Math.PI/2;
		direction = 2*Math.PI*3/4-tmp;
	}

	void leftCollision(double deviation)
	{
		double tmp = direction-Math.PI;
		direction = 2*Math.PI-tmp;
	}

	void rightCollision(double deviation)
	{
		double tmp = direction;
		direction = Math.PI-tmp;
	}
	void normalizeDirection()
	{	
		// Make sure direction is between 0 and 2*PI
		while(direction<0) {
			direction+=(2*Math.PI);
		}
		while(direction>(Math.PI*2)) {
			direction-=(2*Math.PI);
		}
	}

	int move(ActionInterface a, Bat bats[])
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			x+=Math.cos(direction);
			y+=Math.sin(direction);
			score+=handleCollision(bats, a);
		}
		return score;
	}
	void draw(Graphics g)
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
