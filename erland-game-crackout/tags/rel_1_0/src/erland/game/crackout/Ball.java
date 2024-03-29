package erland.game.crackout;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import java.awt.*;
import java.util.*;
import erland.game.GameEnvironmentInterface;

/**
 * Represents a ball
 */
class Ball
{
	/** Horisontal drawing offset */
	protected int offsetX;
	/** Vertical drawing offset */
	protected int offsetY;
	/** X position of the ball */
	protected double x;
	/** Y postition of the ball */
	protected double y;
	/** Width of game area */
	protected int sizeX;
	/** Height of game area */
	protected int sizeY;
	/** Size of ball in pixels */
	protected int ballSize;
	/** Current moving direction of the ball */
	protected double direction;
	/** Current speed of the ball */
	protected int speed;
	/** Minimum angle that the ball always has to have when it collides with something on the top eller bottom side of the ball */
	protected static final double MIN_ANGLE=Math.PI/9;
	/** Image for the ball */
	protected Image img;
			
	/**
	 * Initialize the ball
	 * @param environment Reference to game environment object
	 * @param offsetX Horisontal drawing offset
	 * @param offsetY Vertical drawing offset
	 * @param sizeX Width of the game area
	 * @param sizeY Height of the game area
	 * @param x Initial x position of the ball
	 * @param y Initial y position of the ball
	 * @param ballSize Size of the ball
	 * @param speed Speed of the ball 
	 * @param direction Moving direction of the ball
	 */
	public void init(GameEnvironmentInterface environment, int offsetX, int offsetY, int sizeX, int sizeY, int x, int y, int ballSize, int speed, double direction)
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
		this.img = environment.getImageHandler().getImage("ball.gif");
	}
	
	/**
	 * Move the ball to the specified position
	 * @param x New x position of the ball
	 * @param y New y position of the ball
	 * @see #move(ActionInterface, Bat[], Block[], LinkedList, double, boolean)	 
	 */
	public void move(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Move the ball one step
	 * @param a Reference to an object which implements all collision actions
	 * @param bats An array with all bats
	 * @param blocks An array with all blocks
	 * @param monsters A list with all monsters
	 * @param scoreMultiplier Every increase of the score should be multiplied with this
	 * @param safe If true the ball shall also bounce with it hits the bottom of the game area
	 * @return The number of points the score should be increased with
	 */
	public int move(ActionInterface a,Bat bats[], Block blocks[], LinkedList monsters, double scoreMultiplier, boolean safe)
	{
		int score=0;
		for(int i=0;i<speed;i++) {
			x+=Math.cos(direction);
			y+=Math.sin(direction);
			score += handleCollision(a,bats, blocks, monsters, scoreMultiplier,safe);
		}
		return score;
	}
	
	/**
	 * Check if the ball collides with something
	 * @param a Reference to an object which implements all collision actions
	 * @param bats An array with all bats
	 * @param blocks An array with all blocks
	 * @param monsters A list with all monsters
	 * @param scoreMultiplier Every increase of the score should be multiplied with this
	 * @param safe If true the ball shall also bounce with it hits the bottom of the game area
	 * @return The number of points the score should be increased with
	 */
	protected int handleCollision(ActionInterface a, Bat bats[], Block blocks[], LinkedList monsters, double scoreMultiplier, boolean safe)
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
				a.removeBall(this);
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
	
	/**
	 * Check if the ball collides with the specified rectangle
	 * @param rc CollisionRect object that collision should be checked with
	 * @param deviation Maximum deviation of the bounce normal
	 * @return true/false (Collision/No collision)
	 */
	protected boolean checkCollision(CollisionRect rc, double deviation)
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
	/**
	 * Change the angle due to a collistion on the top of the ball
	 * @param deviation Deviation angle of the bounce normal
	 */
	protected void topCollision(double deviation)
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
	/**
	 * Change the angle due to a collistion on the bottom of the ball
	 * @param deviation Deviation angle of the bounce normal
	 */
	protected void bottomCollision(double deviation)
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
	/**
	 * Change the angle due to a collistion on the left side of the ball
	 * @param deviation Deviation angle of the bounce normal (Currently not implemented)
	 */
	protected void leftCollision(double deviation)
	{
		double tmp = direction-Math.PI;
		direction = 2*Math.PI-tmp;
	}
	/**
	 * Change the angle due to a collistion on the right side of the ball
	 * @param deviation Deviation angle of the bounce normal (Currently not implemented)
	 */
	protected void rightCollision(double deviation)
	{
		double tmp = direction;
		direction = Math.PI-tmp;
	}
	
	/**
	 * Normalize the ball moving direction so it is an angle between 0 and 2*Math.PI
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
	 * Draw the ball on the specified Graphics object
	 * @param g The Graphics object to draw on
	 */
	public void draw(Graphics g)
	{
		//g.setColor(new Color(0x444444));
		//g.fillOval((int)(offsetX+x),(int)(offsetY+y),ballSize,ballSize);
		//g.setColor(new Color(0x888888));
		//g.fillOval((int)(offsetX+x+1),(int)(offsetY+y+1),ballSize-2,ballSize-2);
		//g.setColor(new Color(0xBBBBBB));
		//g.fillOval((int)(offsetX+x+2),(int)(offsetY+y+2),ballSize-4,ballSize-4);
		//g.setColor(Color.white);
		//g.fillOval((int)(offsetX+x+4),(int)(offsetY+y+4),ballSize-8,ballSize-8);
		g.drawImage(img,(int)(offsetX+x),(int)(offsetY+y),null);
		
	}
	
	/**
	 * Set the speed of the ball
	 * @param speed The new speed of the ball
	 */
	public void setSpeed(int speed)
	{
		if(speed>=1) {
			if(speed>10) {
				speed = 10;
			}
			this.speed = speed;
		}
	}
	
	/**
	 * Get the speed of the ball
	 * @return The current speed of the ball
	 */
	public int getSpeed()
	{
		return this.speed;
	}	
}