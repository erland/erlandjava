package erland.game.crackout;

import java.awt.*;
import java.util.*;

public class Ball
{
	int offsetX;
	int offsetY;
	double x;
	double y;
	int sizeX;
	int sizeY;
	int ballSize;
	double direction;
	int speed;
	boolean active;
	static final double MIN_ANGLE=Math.PI/9;
	ImageHandlerInterface images;
			
	void init(ImageHandlerInterface images, int offsetX, int offsetY, int sizeX, int sizeY, int x, int y, int ballSize, int speed, double direction)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		this.ballSize = ballSize;
		this.images = images;
		active=true;
	}
	
	void move(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	int move(ActionInterface a,Bat bats[], Block blocks[], LinkedList monsters, double scoreMultiplier, boolean safe)
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			x+=Math.cos(direction);
			y+=Math.sin(direction);
			score += handleCollision(a,bats, blocks, monsters, scoreMultiplier,safe);
		}
		return score;
	}
	
	int handleCollision(ActionInterface a, Bat bats[], Block blocks[], LinkedList monsters, double scoreMultiplier, boolean safe)
	{
		int score =0;
		// Handle collsion with walls
		if(x<0) {
			leftCollision(0);
			x=0;
		}else if(x>(sizeX-ballSize)) {
			rightCollision(0);
			x=sizeX-ballSize;
		}
		
		if(y<0) {
			topCollision(0);
			y=0;
		}else if(y>(sizeY-ballSize)) {
			if(safe) {
				bottomCollision(0);
			}else {
				active=false;
				a.RemoveBall(this);
			}
			y=sizeY-ballSize;
		}
		
		// Check collision with bats
		for(int i=0;i<bats.length;i++) {
			if(bats[i]!=null) {
				checkCollision(bats[i],Math.PI/4);
			}
		}
		
		// Check collision with blocks
		if(blocks!=null) {
			for(int i=0;i<blocks.length;i++) {
				if(checkCollision(blocks[i],0)) {
					blocks[i].handleCollision(a);
					score += scoreMultiplier*blocks[i].getScore();
				}
			}
		}
		normalizeDirection();
		
		// Check collistion with monsters
		if(monsters!=null) {
			ListIterator it = monsters.listIterator();
			while(it.hasNext()) {
				Monster m = (Monster)(it.next());
				if(checkCollision(m,0)) {
					m.handleCollision(a);
				}
			}
		}

		normalizeDirection();
		return score;
	}
	boolean checkCollision(CollisionRect rc, double deviation)
	{
		boolean bCollision = false;
		if((x+ballSize)>rc.left() && x<rc.right()) {
			if((y+ballSize)>rc.top() && y<rc.bottom()) {
				double diffRight = x+ballSize-rc.left();
				double diffLeft = rc.right()-x;
				double diffBottom = y+ballSize-rc.top();
				double diffTop = rc.bottom()-y;
				
				if(direction>=0 && direction<(Math.PI/2)) {
					if(diffBottom<diffTop && diffBottom<diffRight && diffBottom<diffLeft) {
						bottomCollision(deviation*((x+ballSize/2-(rc.left()+(rc.right()-rc.left())/2))/((rc.right()-rc.left())/2+ballSize/2)));
						y=rc.top()-ballSize;
						bCollision = true;
					}
					if(diffRight<diffLeft && diffRight<diffTop && diffRight<diffBottom) {
						rightCollision(deviation);
						x=rc.left()-ballSize;
						bCollision = true;
					}
				}else if(direction>=(Math.PI/2) && direction <Math.PI) {
					if(diffBottom<diffTop && diffBottom<diffRight && diffBottom<diffLeft) {
						bottomCollision(deviation*((x+ballSize/2-(rc.left()+(rc.right()-rc.left())/2))/((rc.right()-rc.left())/2+ballSize/2)));
						y=rc.top()-ballSize;
						bCollision = true;
					}
					if(diffLeft<diffRight && diffLeft<diffTop && diffLeft<diffBottom) {
						leftCollision(deviation);
						x=rc.right();
						bCollision = true;
					}
				}else if(direction>=Math.PI && direction<(2*Math.PI*3/4)) {
					if(diffTop<diffBottom && diffTop<diffRight && diffTop<diffLeft) {
						topCollision(deviation);
						y=rc.bottom();
						bCollision = true;
					}
					if(diffLeft<diffRight && diffLeft<diffTop && diffLeft<diffBottom) {
						leftCollision(deviation);
						x=rc.right();
						bCollision = true;
					}
				}else if(direction>=(2*Math.PI*3/4) && direction<360) {
					if(diffTop<diffBottom && diffTop<diffRight && diffTop<diffLeft) {
						topCollision(deviation);
						y=rc.bottom();
						bCollision = true;
					}
					if(diffRight<diffLeft && diffRight<diffTop && diffRight<diffBottom) {
						rightCollision(deviation);
						x=rc.left()-ballSize;
						bCollision = true;
					}
				}
			}
		}
		return bCollision;
	}
	void topCollision(double deviation)
	{
		double tmp = direction-2*Math.PI*3/4;
		direction = Math.PI/2-tmp;
		
		// Make sure we allways have a small angle
		normalizeDirection();
		if(Math.abs(direction-Math.PI)<(MIN_ANGLE)) {
			direction=Math.PI-MIN_ANGLE;
		}else if(direction<(MIN_ANGLE)) {
			direction=MIN_ANGLE;
		}else if(direction>(2*Math.PI-MIN_ANGLE)) {
			direction=MIN_ANGLE;
		}
	}
	void bottomCollision(double deviation)
	{
		double tmp = direction-Math.PI/2;
		if(Math.abs(tmp)>(Math.PI/4)) {
			deviation=(deviation*((Math.PI/2-Math.abs(tmp))/(Math.PI/4)));
		}else {
			deviation=(deviation*(0.5+0.5*Math.abs(tmp/(Math.PI/4))));
		}
		tmp=direction-(Math.PI/2+deviation);
		direction = (2*Math.PI*3/4+deviation)-tmp;

		// Make sure we allways have a small angle
		normalizeDirection();
		if(Math.abs(direction-Math.PI)<(MIN_ANGLE)) {
			direction=Math.PI+MIN_ANGLE;
		}else if(direction<(MIN_ANGLE)) {
			direction=2*Math.PI-MIN_ANGLE;
		}else if(direction>(2*Math.PI-MIN_ANGLE)) {
			direction=2*Math.PI-MIN_ANGLE;
		}
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
	
	void draw(Graphics g)
	{
		//g.setColor(new Color(0x444444));
		//g.fillOval((int)(offsetX+x),(int)(offsetY+y),ballSize,ballSize);
		//g.setColor(new Color(0x888888));
		//g.fillOval((int)(offsetX+x+1),(int)(offsetY+y+1),ballSize-2,ballSize-2);
		//g.setColor(new Color(0xBBBBBB));
		//g.fillOval((int)(offsetX+x+2),(int)(offsetY+y+2),ballSize-4,ballSize-4);
		//g.setColor(Color.white);
		//g.fillOval((int)(offsetX+x+4),(int)(offsetY+y+4),ballSize-8,ballSize-8);
		g.drawImage(images.getImage(images.BALL),(int)(offsetX+x),(int)(offsetY+y),null);
		
	}
	boolean isActive()
	{
		return active;
	}
	
	void setSpeed(int speed)
	{
		if(speed>=1) {
			if(speed>10) {
				speed = 10;
			}
			this.speed = speed;
		}
	}
	
	int getSpeed()
	{
		return this.speed;
	}
	
}